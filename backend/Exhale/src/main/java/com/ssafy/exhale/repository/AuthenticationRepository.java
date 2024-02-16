package com.ssafy.exhale.repository;

import com.ssafy.exhale.domain.Authentication;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AuthenticationRepository extends MongoRepository <Authentication,String>{
    long deleteByMemberIdAndKey(Long memberId, String key);
    Optional<Authentication> findByMemberIdAndKey(Long memberId, String key);
    long deleteByMemberId(Long memberId);
}
