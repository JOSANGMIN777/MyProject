package com.ssafy.exhale.service;

import com.ssafy.exhale.domain.Authentication;
import com.ssafy.exhale.dto.logicDto.AuthenticationDto;
import com.ssafy.exhale.exception.handler.NoSuchDataException;
import com.ssafy.exhale.exception.handler.UserPermissionException;
import com.ssafy.exhale.repository.AuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationRepository authenticationRepository;

    @Value("${Spring.jwt.refresh-token}")
    private long expireAt;

    public boolean saveRefreshValue(AuthenticationDto authenticationDto) {
        authenticationDto.setExpireAt(expireAt);
        Authentication authentication = authenticationDto.toEntity();
        authenticationRepository.save(authentication);
        // todo 예외 처리
        return true;
    }

    @Transactional
    public void reSaveRefreshValue(AuthenticationDto authenticationDto) {
        if(removeRefreshValue(authenticationDto.getMemberId(), authenticationDto.getKey()) == 0) {
            // todo 예외처리
        }
        saveRefreshValue(authenticationDto);
    }

    public boolean compareRefreshToken(Long memberId, String tokenValue, String key) {
        return authenticationRepository.findByMemberIdAndKey(memberId, key)
                .map(auth -> auth.getRefreshValue().equals(tokenValue))
                .orElseGet(() -> {
                    // TODO: 예외 처리
                    return false;
                });
    }

    public long removeRefreshValue(Long memberId, String key) {
        long cnt = authenticationRepository.deleteByMemberIdAndKey(memberId, key);
        return cnt;
    }

    public void logout(long memberId, String key) {
        long cnt = authenticationRepository.deleteByMemberIdAndKey(memberId, key);
        if(cnt == 0) {
            throw new UserPermissionException();
        }
    }
}