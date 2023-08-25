package com.health.springbootback.repository;

import com.health.springbootback.dto.UserInfoDto;
import com.health.springbootback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


// DAO
// 자동으로 bean 등록이 된다(@Repository 생략 가능)
public interface UserRepository extends JpaRepository<User, Long> {
    User findByNickname(String nickname);

    int countByNicknameLike(String nickname);

    boolean existsByNickname(String nickname);
}
