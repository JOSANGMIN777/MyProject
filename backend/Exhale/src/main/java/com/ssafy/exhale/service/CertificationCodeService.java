package com.ssafy.exhale.service;

import com.ssafy.exhale.domain.CertificationCode;
import com.ssafy.exhale.dto.logicDto.CertificationCodeDto;
import com.ssafy.exhale.dto.requestDto.CertificationCodeRequest;
import com.ssafy.exhale.exception.handler.NoSuchDataException;
import com.ssafy.exhale.repository.CertificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CertificationCodeService {
    private final CertificationCodeRepository certificationCodeRepository;

    @Transactional
    public void saveCode(CertificationCodeDto certificationCodeDto){
        certificationCodeRepository.deleteByEmail(certificationCodeDto.getEmail());
        CertificationCode certificationCode = certificationCodeDto.toEntity();
        certificationCodeRepository.save(certificationCode);
    }

    public boolean compareCode(CertificationCodeRequest certificationCodeRequest){
        String email = certificationCodeRequest.getEmailId() +"@"+ certificationCodeRequest.getEmailDomain();
        String code = certificationCodeRequest.getCode();
        CertificationCode certification = certificationCodeRepository.findByEmail(email).orElseThrow(NoSuchDataException::new);

        if(!certification.getCode().equals(code)) return false;
        certificationCodeRepository.deleteById(certification.getId());
        return true;
    }
}
