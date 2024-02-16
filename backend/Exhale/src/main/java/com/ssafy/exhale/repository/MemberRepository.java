package com.ssafy.exhale.repository;

import com.ssafy.exhale.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByLoginId(String login_id);
    Member findByLoginIdAndWithdrawFalse(String login_id);

    Boolean existsByEmailIdAndEmailDomain(String emailId, String emailDomain);

    Boolean existsByLoginIdAndWithdrawFalse(String loginId);

    Optional<Member> findByLoginIdAndEmailIdAndEmailDomainAndWithdrawFalse(String loginId, String emailId, String emailDomain);

    Optional<Member> findByLoginIdAndWithdrawIs(String login_id, boolean withDrawIs);

    Optional<Member> findByEmailIdAndEmailDomain(String emailId, String emailDomain);
}
