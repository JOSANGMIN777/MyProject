package com.ssafy.exhale.controller;

import com.ssafy.exhale.dto.requestDto.FluencyCheckRequest;
import com.ssafy.exhale.dto.requestDto.LetterRecodeRequest;
import com.ssafy.exhale.dto.requestDto.RegisterReviewRequest;
import com.ssafy.exhale.dto.requestDto.SolvedProblemRequest;
import com.ssafy.exhale.dto.responseDto.commonDto.CommonResponse;
import com.ssafy.exhale.dto.responseDto.rehabilitationDto.ReviewProblemResponse;
import com.ssafy.exhale.exception.handler.InValidParameterException;
import com.ssafy.exhale.service.RehabilitationService;
import com.ssafy.exhale.util.TokenPayloadUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rehabilitation")
@RequiredArgsConstructor
public class CourseController {

    private final RehabilitationService rehabilitationService;
    private final TokenPayloadUtil tokenPayloadUtil;

    @GetMapping
    public ResponseEntity<CommonResponse> getCourseList() {
        return CommonResponse.ok(rehabilitationService.getCourseList());
    }

    @GetMapping("/{course_id}")
    public ResponseEntity<CommonResponse> getCategoryList(@PathVariable("course_id") int courseId) {
        return CommonResponse.ok(rehabilitationService.getCategory(courseId));
    }

    @GetMapping("/problem/{category_id}")
    public ResponseEntity<CommonResponse> getProblemList(@PathVariable("category_id") int categoryId) {
        return CommonResponse.ok(rehabilitationService.getProblemList(categoryId, tokenPayloadUtil.getLoginId()));
    }

    @PostMapping("/result")
    public ResponseEntity<CommonResponse> solveProblem(@Validated @RequestBody SolvedProblemRequest solvedProblemRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new InValidParameterException();
        }
        rehabilitationService.solveProblem(solvedProblemRequest, tokenPayloadUtil.getLoginId());
        return CommonResponse.ok(null);
    }

    @PostMapping("/fluency-check")
    public ResponseEntity<CommonResponse> fluencyCheck(@Validated @RequestBody FluencyCheckRequest fluencyCheckRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new InValidParameterException();
        }
        return CommonResponse.ok(rehabilitationService.fluencyCheck(fluencyCheckRequest));
    }

    @PostMapping("/review")
    public ResponseEntity<CommonResponse> registerReview(@Validated @RequestBody RegisterReviewRequest registerReviewRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new InValidParameterException();
        }
        rehabilitationService.registerReview(registerReviewRequest.getProblemId(), tokenPayloadUtil.getLoginId());
        return CommonResponse.ok(null);
    }

    @DeleteMapping("/review/{problem_id}")
    public ResponseEntity<CommonResponse> deleteReview(@PathVariable("problem_id") long problemId) {
        rehabilitationService.deleteReview(problemId, tokenPayloadUtil.getLoginId());
        return CommonResponse.ok(null);
    }

    @GetMapping("/review-problem/{course_id}")
    public ResponseEntity<CommonResponse> getReviewProblemList(@PathVariable("course_id") long course_id) {
        List<ReviewProblemResponse> problemResponseList = rehabilitationService.getReviewProblemList(course_id, tokenPayloadUtil.getLoginId());
        return CommonResponse.ok(problemResponseList);
    }

    @GetMapping("/letter")
    public ResponseEntity<CommonResponse> getLetterList() {
        return CommonResponse.ok(rehabilitationService.getLetterList());
    }

    @PostMapping("/letter/result")
    public ResponseEntity<CommonResponse> registerLetterRecode(@Valid @RequestBody List<LetterRecodeRequest> letterRecodeRequestList, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new InValidParameterException();
        }

        rehabilitationService.registerLetterRecode(letterRecodeRequestList, tokenPayloadUtil.getLoginId());
        return CommonResponse.ok(null);
    }
}
