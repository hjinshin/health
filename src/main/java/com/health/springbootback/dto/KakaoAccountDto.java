package com.health.springbootback.dto;

import lombok.Data;

@Data
public class KakaoAccountDto {
    public Long id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;

    @Data
    public static class Properties {
        public String nickname;
    }

    @Data
    public static class KakaoAccount {
        public Boolean profile_nickname_needs_agreement;
        public Profile profile;
    }

    @Data
    public static class Profile {
        public String nickname;
    }
}
