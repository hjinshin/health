package com.health.springbootback.entity;

import com.health.springbootback.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity // user 클래스가 mysql에 테이블로 생성
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private Long uid;    // 아이디(카카오 인증 아이디)

    @Column(nullable = false, length = 10)
    private String nickname;  // 닉네임

    @Enumerated(EnumType.STRING)
    private RoleType role;    // 권한(ADMIN, USER)

    @CreationTimestamp // 시간이 자동 입력
    private Timestamp createDate;
}
