package com.health.springbootback.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.health.springbootback.dto.*;
import com.health.springbootback.entity.User;
import com.health.springbootback.enums.RoleType;
import com.health.springbootback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Service
public class AuthService {

    private final UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Value("${kakao_client_id}")
    private String client_id;
    @Value("${kakao_redirect_uri}")
    private String redirect_uri;
    @Value("${admin_passwd}")
    private String admin_passwd;
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

    public User getKakaoProfile(String kakaoAccessToken) throws HttpStatusCodeException, JsonProcessingException {
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
        ResponseEntity<String> response;

        try {
            response = rt.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.POST,
                    kakaoInfoRequest,
                    String.class
            );


            ObjectMapper objectMapper = new ObjectMapper();
            KakaoAccountDto kakaoAccountDto = objectMapper.readValue(response.getBody(), KakaoAccountDto.class);

            String name = kakaoAccountDto.getKakao_account().getProfile().getNickname();
            int num = userService.countNickname(name) + 1;

            return User.builder()
                    .uid(kakaoAccountDto.getId())
                    .nickname(name + num)
                    .build();

        } catch (HttpStatusCodeException e) {
            // Handle different HTTP status code errors here
            // You can extract the status code using e.getStatusCode()
            System.out.println(e.getStatusCode());
            throw e;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ResponseEntity<LoginResponseDto> kakaoLogin(String kakaoAccessToken){
        HttpHeaders headers = new HttpHeaders();
        try {
            User user = getKakaoProfile(kakaoAccessToken);

            UserInfoDto userInfoDto;
            User existUser = userService.findMember(user.getUid());
            try {
                if(existUser == null) {
                    //System.out.println("처음 로그인 하는 회원입니다.");
                    userService.signUp(user);
                }

                headers.add("Content-type", "application/json");

                ResponseCookie cookie = ResponseCookie.from("access_token", kakaoAccessToken)
                        .httpOnly(true)
                        .secure(true)
                        .domain("https://web-health-6w1j2alm0gkf3r.sel5.cloudtype.app")
                        .path("/")
                        .build();
                headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

                userInfoDto = userService.findNicknameAndRoleById(user.getUid());
                LoginResponseDto loginResponseDto = new LoginResponseDto(true, userInfoDto);

                return ResponseEntity.ok().headers(headers).body(loginResponseDto);
            } catch (Exception e) {
                LoginResponseDto loginResponseDto = new LoginResponseDto(false, null);
                return ResponseEntity.badRequest().body(loginResponseDto);
            }
        } catch(JsonProcessingException e) {
            LoginResponseDto loginResponseDto = new LoginResponseDto(false, null);
            return ResponseEntity.badRequest().body(loginResponseDto);
        }

    }

    public ResponseEntity<MsgResponseDto> kakaoLogout(String kakaoAccessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoAccessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoInfoRequest =
                new HttpEntity<>(headers);

        // POST방식으로 key=value 데이터를 요청(카카오쪽으로)
        RestTemplate rt = new RestTemplate();
        // Http 요청하기 - POST - response 변수에 응답받음
        ResponseEntity<String> response;

        response = rt.exchange(
                "https://kapi.kakao.com/v1/user/logout",
                HttpMethod.POST,
                kakaoInfoRequest,
                String.class
        );
        return ResponseEntity.ok().body(new MsgResponseDto(true, "로그아웃 성공"));
    }

    public ResponseEntity<MsgResponseDto> adminAuth(Long uid, String passwd) {
        User user = userRepository.findById(uid).get();
        if(Objects.equals(passwd, admin_passwd)){
            userRepository.save(new User(user.getUid(), user.getNickname(), RoleType.ADMIN, user.getCreateDate()));
            return ResponseEntity.ok().body(new MsgResponseDto(true, "관리자 인증 완료"));
        }
        else
            return ResponseEntity.badRequest().body(new MsgResponseDto(false, "비밀번호가 틀렸습니다"));
    }
}
