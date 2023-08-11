package com.health.springbootback.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.health.springbootback.dto.KakaoAccountDto;
import com.health.springbootback.dto.KakaoTokenDto;
import com.health.springbootback.dto.LoginResponseDto;
import com.health.springbootback.dto.UserInfoDto;
import com.health.springbootback.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private final UserService userService;

    @Value("${kakao_client_id}")
    private String client_id;
    @Value("${kakao_redirect_uri}")
    private String redirect_uri;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public String getKakaoAccessToken(String code) {
        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client_id);
        params.add("redirect_uri", redirect_uri);
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // POST방식으로 key=value 데이터를 요청(카카오쪽으로)
        RestTemplate rt = new RestTemplate();
        // Http 요청하기 - POST - response 변수에 응답받음
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // json parsing
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoTokenDto kakaoTokenDto = null;
        try {
            kakaoTokenDto = objectMapper.readValue(response.getBody(), KakaoTokenDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assert kakaoTokenDto != null;

        return kakaoTokenDto.getAccess_token();
    }

    public User getKakaoProfile(String kakaoAccessToken) {
        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoAccessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoInfoRequest =
                new HttpEntity<>(headers);

        // POST방식으로 key=value 데이터를 요청(카카오쪽으로)
        RestTemplate rt = new RestTemplate();
        // Http 요청하기 - POST - response 변수에 응답받음
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoInfoRequest,
                String.class
        );

        // json parsing
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoAccountDto kakaoAccountDto = null;
        try {
            kakaoAccountDto = objectMapper.readValue(response.getBody(), KakaoAccountDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assert kakaoAccountDto != null;
        return User.builder()
                .uid(kakaoAccountDto.getId())
                .nickname(kakaoAccountDto.getKakao_account().getProfile().getNickname())
                .build();
    }

    public ResponseEntity<LoginResponseDto> kakaoLogin(String kakaoAccessToken) {
        User user = getKakaoProfile(kakaoAccessToken);

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        UserInfoDto userInfoDto = new UserInfoDto();
        User existUser = userService.findMember(user.getUid());
        try {
            if(existUser == null) {
                System.out.println("처음 로그인 하는 회원입니다.");
                userService.signUp(user);
            }

            userInfoDto = userService.findNicknameAndRoleById(user.getUid());
            loginResponseDto.setUserInfo(userInfoDto);
            loginResponseDto.setLoginSuccess(true);

            return ResponseEntity.ok().body(loginResponseDto);
        } catch (Exception e) {
            loginResponseDto.setLoginSuccess(false);
            return ResponseEntity.badRequest().body(loginResponseDto);
        }
    }
}
