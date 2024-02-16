package com.ssafy.exhale.repository;

import com.ssafy.exhale.domain.CertificationCode;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CertificationCodeRepository extends MongoRepository<CertificationCode, String> {
    Optional<CertificationCode> findByEmail(String email);
    void deleteByEmail(String email);
}
