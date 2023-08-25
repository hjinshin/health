package com.health.springbootback.service;

import com.health.springbootback.dto.UserInfoDto;
import com.health.springbootback.enums.RoleType;
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

    @Transactional
    public void updateNickname(Long uid, String nickname) {
        User user = userRepository.findById(uid).get();
        User new_user = new User(user.getUid(), nickname, user.getRole(), user.getCreateDate());
        System.out.println("닉네임 변경: " + user.getNickname() + " -> " + nickname);
        userRepository.save(new_user);
    }

    @Transactional(readOnly = true)
    public User findMember(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public UserInfoDto findNicknameAndRoleById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        assert user != null;
        return new UserInfoDto(user.getNickname(), user.getRole());
    }

    @Transactional(readOnly = true)
    public Long findUidByNickname(String nickname) {
        User user = userRepository.findByNickname(nickname);
        if(user == null) {
            return (long) -1;
        }
        return user.getUid();
    }

    @Transactional(readOnly = true)
    public User findUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public int countNickname(String nickname) {
        return userRepository.countByNicknameLike(nickname);
    }
}
