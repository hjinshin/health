package com.health.springbootback.service;

import com.health.springbootback.dto.LoginResponseDto;
import com.health.springbootback.dto.UserInfoDto;
import com.health.springbootback.entity.RoleType;
import com.health.springbootback.entity.User;
import com.health.springbootback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void signUp(User user) {
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findMember(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserInfoDto findNicknameAndRoleById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        assert user != null;
        return new UserInfoDto(user.getNickname(), user.getRole());
    }
}
