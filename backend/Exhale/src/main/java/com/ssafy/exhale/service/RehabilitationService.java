package com.ssafy.exhale.service;

import com.ssafy.exhale.domain.Letter;
import com.ssafy.exhale.domain.LetterRecode;
import com.ssafy.exhale.domain.Member;
import com.ssafy.exhale.domain.rehabilitation.*;
import com.ssafy.exhale.dto.requestDto.FluencyCheckRequest;
import com.ssafy.exhale.dto.requestDto.LetterRecodeRequest;
import com.ssafy.exhale.dto.requestDto.SolvedProblemRequest;
import com.ssafy.exhale.dto.responseDto.rehabilitationDto.*;
import com.ssafy.exhale.exception.handler.DuplicateDataException;
import com.ssafy.exhale.exception.handler.NoSuchDataException;
import com.ssafy.exhale.repository.MemberRepository;
import com.ssafy.exhale.repository.rehabilitationRepository.*;
import com.ssafy.exhale.util.ChatGptUtil;
import com.ssafy.exhale.util.S3Util;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RehabilitationService {
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final ProblemRepository<Problem> problemRepository;
    private final SolvedProblemRepository solvedProblemRepository;
    private final ReviewRepository reviewRepository;
    private final LetterRepository letterRepository;
    private final LetterRecodeRepository letterRecodeRepository;

    private final ChatGptUtil chatGptUtil;

    public List<CourseResponse> getCourseList() {
        List<CourseResponse> courseResponseList = new ArrayList<>();
        for(Course course : courseRepository.findByIsRemoved(false)) {
            courseResponseList.add(CourseResponse.from(course));
        }
        return courseResponseList;
    }

    public List<CategoryResponse> getCategory(long courseId) {
        Optional<Course> courseOpt = courseRepository.findByIdAndIsRemoved(courseId, false);
        Course course = courseOpt.orElseThrow(NoSuchDataException::new);

        List<CategoryResponse> categoryResponsesList = new ArrayList<>();
        for(Category category : categoryRepository.findByCourseAndIsRemoved(course, false)) {
            categoryResponsesList.add(CategoryResponse.from(category));
        }
        return categoryResponsesList;
    }

    public ProblemGetResponse getProblemList(long categoryId, String loginId) {
        Optional<Category> categoryOpt = categoryRepository.findByIdAndIsRemoved(categoryId, false);
        Category category = categoryOpt.orElseThrow(NoSuchDataException::new);

        List<Problem> problemList = problemRepository.findByCategoryAndIsRemovedOrderByCreatedDate(category, false);

        List<Long> lastSolvedProblem = solvedProblemRepository.findLastSolvedProblem(category.getId(), loginId, PageRequest.of(0, 1));
        long lastSolvedProblemId = lastSolvedProblem.isEmpty() ? -1 : lastSolvedProblem.get(0);

        int firstProblemIndex = 0;
        List<ProblemResponse> problemResponseList = new ArrayList<>();
        for(int order=0; order<problemList.size(); order++) {
            Problem problem = problemList.get(order);
            problemResponseList.add(ProblemResponse.from(problem));
            if(problem.getId() == lastSolvedProblemId) firstProblemIndex = order + 1;
        }
        if(firstProblemIndex >= problemList.size()) firstProblemIndex = 0;

        return ProblemGetResponse.from(problemResponseList, firstProblemIndex);
    }

    @Transactional
    public void solveProblem(SolvedProblemRequest solvedProblemRequest, String loginId) {
        Optional<Problem> problemOpt = problemRepository.findByIdAndIsRemoved(solvedProblemRequest.getProblemId(), false);
        Problem problem = problemOpt.orElseThrow(NoSuchDataException::new);

        Optional<Member> memberOpt = memberRepository.findByLoginIdAndWithdrawIs(loginId, false);
        Member member = memberOpt.orElseThrow(NoSuchDataException::new);

        //오답, 복습에 추가
        if(!solvedProblemRequest.getIsRight()) {
            Optional<Review> reviewOpt = reviewRepository.findByMemberAndProblem(member, problem);
            if(reviewOpt.isEmpty()) {
                Review review = Review.of(member, problem);
                reviewRepository.save(review);
            }
        }

        SolvedProblem solvedProblem = SolvedProblem.of(
                solvedProblemRequest.getIsRight(),
                solvedProblemRequest.getTime(),
                member,
                problem);
        solvedProblemRepository.save(solvedProblem);
    }

    @Transactional
    public void registerReview(long problemId, String loginId) {
        Optional<Problem> problemOpt = problemRepository.findByIdAndIsRemoved(problemId, false);
        Problem problem = problemOpt.orElseThrow(NoSuchDataException::new);

        Optional<Member> memberOpt = memberRepository.findByLoginIdAndWithdrawIs(loginId, false);
        Member member = memberOpt.orElseThrow(NoSuchDataException::new);

        Optional<Review> reviewOpt = reviewRepository.findByMemberAndProblem(member, problem);
        if(reviewOpt.isPresent()) throw new DuplicateDataException();

        Review review = Review.of(member, problem);
        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(long problemId, String loginId) {
        Optional<Problem> problemOpt = problemRepository.findByIdAndIsRemoved(problemId, false);
        Problem problem = problemOpt.orElseThrow(NoSuchDataException::new);

        Optional<Member> memberOpt = memberRepository.findByLoginIdAndWithdrawIs(loginId, false);
        Member member = memberOpt.orElseThrow(NoSuchDataException::new);

        Optional<Review> reviewOpt = reviewRepository.findByMemberAndProblem(member, problem);
        Review review = reviewOpt.orElseThrow(NoSuchDataException::new);

        reviewRepository.delete(review);
    }

    public List<ReviewProblemResponse> getReviewProblemList(long courseId, String loginId) {
        Optional<Course> courseOpt = courseRepository.findByIdAndIsRemoved(courseId, false);
        Course course = courseOpt.orElseThrow(NoSuchDataException::new);

        Optional<Member> memberOpt = memberRepository.findByLoginIdAndWithdrawIs(loginId, false);
        Member member = memberOpt.orElseThrow(NoSuchDataException::new);

        List<ReviewProblemResponse> reviewProblemResponseList = new ArrayList<>();
        for(Problem problem : problemRepository.getReviewProblem(courseId, loginId)) {
            reviewProblemResponseList.add(ReviewProblemResponse.from(problem));
        }
        return reviewProblemResponseList;
    }

    public FluencyCheckResponse fluencyCheck(FluencyCheckRequest fluencyCheckRequest) {
        return chatGptUtil.postRequest(fluencyCheckRequest.getQuestion(), fluencyCheckRequest.getAnswer());
    }

    public List<LetterResponse> getLetterList() {
        List<LetterResponse> letterResponseList = new ArrayList<>();
        for(Letter letter : letterRepository.findAll()) {
            letterResponseList.add(LetterResponse.of(letter));
        }
        return letterResponseList;
    }

    @Transactional
    public void registerLetterRecode(List<LetterRecodeRequest> letterRecodeRequestList, String loginId) {
        for(LetterRecodeRequest letterRecodeRequest : letterRecodeRequestList) {
            Optional<Letter> letterOpt = letterRepository.findById(letterRecodeRequest.getLetterId());
            Letter letter = letterOpt.orElseThrow(NoSuchDataException::new);

            Optional<Member> memberOpt = memberRepository.findByLoginIdAndWithdrawIs(loginId, false);
            Member member = memberOpt.orElseThrow(NoSuchDataException::new);

            LetterRecode letterRecode = LetterRecode.of(
                    letterRecodeRequest.getCorrectCnt(),
                    letterRecodeRequest.getWrongCnt(),
                    member,
                    letter
            );
            letterRecodeRepository.save(letterRecode);
        }
    }
}
