package com.ssafy.exhale.service;

import com.ssafy.exhale.dto.logicDto.KakaoInfoDto;
import com.ssafy.exhale.dto.logicDto.KakaoTokenDto;
import com.ssafy.exhale.dto.logicDto.MemberDto;
import com.ssafy.exhale.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OauthService {
    @Value("${oauth.KAKAO_ID}")
    private String KAKAO_ID;
    @Value("${oauth.KAKAO_REDIRECT_URL}")
    private String KAKAO_REDIRECT_URL;
    @Value("${oauth.KAKAO_TOKEN_REQUEST_URL}")
    private String KAKAO_TOKEN_REQUEST_URL;
    @Value("${oauth.KAKAO_USER_INFO_REQUEST_URL}")
    private String KAKAO_USER_INFO_REQUEST_URL;

    private final MemberRepository memberRepository;

    public ResponseEntity<KakaoTokenDto> requestKakaoToken(String code, String redirectType) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders(); // 헤더에 key들을 담아준다.
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", KAKAO_ID);
        params.add("redirect_uri", KAKAO_REDIRECT_URL + redirectType);
        params.add("code", code);

        // 해더와 바디를 하나의 오브젝트로 만들기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // Http 요청하고 리턴값을 response 변수로 받기
        System.out.println("redirect_uri = " + params.get("redirect_uri"));
        return restTemplate.exchange(
                KAKAO_TOKEN_REQUEST_URL, // Host
                HttpMethod.POST, // Request Method
                kakaoTokenRequest,    // RequestBody
                KakaoTokenDto.class);
    }

    public KakaoInfoDto requestKakaoUserInfo(KakaoTokenDto kakaoTokenDto) throws ParseException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders(); // 헤더에 key들을 담아준다.
        headers.setBearerAuth(kakaoTokenDto.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 해더와 바디를 하나의 오브젝트로 만들기
        HttpEntity<String> kakaoInfoRequest =
                new HttpEntity<>("", headers);

        // Http 요청하고 리턴값을 response 변수로 받기
        String kakaoInfoResponseStr = restTemplate.exchange(
                KAKAO_USER_INFO_REQUEST_URL, // Host
                HttpMethod.POST, // Request Method
                kakaoInfoRequest,    // RequestBody
                String.class).getBody();

        JSONParser parser = new JSONParser();
        JSONObject kakaoInfoResponse = (JSONObject) parser.parse(kakaoInfoResponseStr);
        JSONObject kakaoAcount = (JSONObject) kakaoInfoResponse.get("kakao_account");
        JSONObject profile = (JSONObject) kakaoAcount.get("profile");
        String[] email = ((String) kakaoAcount.get("email")).split("@");
        String emailId = email[0];
        String emailDomain = email[1];

        return KakaoInfoDto.of(
                (String) profile.get("nickname"),
                emailId,
                emailDomain
        );
    }

    public MemberDto findUserByEmail(KakaoInfoDto kakaoInfoDto){
        if(memberRepository.findByEmailIdAndEmailDomain(kakaoInfoDto.getEmailId(), kakaoInfoDto.getEmailDomain()).isEmpty()){
            return null;
        }
        return MemberDto.from(memberRepository.findByEmailIdAndEmailDomain(
                kakaoInfoDto.getEmailId(),
                kakaoInfoDto.getEmailDomain())
                .get());
    }
}
