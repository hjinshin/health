package com.health.springbootback.service;

import com.health.springbootback.dto.UserInfoDto;
import com.health.springbootback.enums.RoleType;
import com.health.springbootback.entity.User;
import com.health.springbootback.repository.UserRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final ResourceLoader resourceLoader;

    public UserService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Transactional
    public void signUp(User user) throws IOException {
        user.setRole(RoleType.USER);
        Resource resource = resourceLoader.getResource("classpath:image/default.png");
        try (InputStream inputStream = resource.getInputStream()) {
            user.setImageData(IOUtils.toByteArray(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
        userRepository.save(user);
    }

    @Transactional
    public void updateNickname(Long uid, String nickname) {
        User user = userRepository.findById(uid).get();
        User new_user = new User(user.getUid(), nickname, user.getRole(), user.getCreateDate(), user.getImageData());
        System.out.println("닉네임 변경: " + user.getNickname() + " -> " + nickname);
        userRepository.save(new_user);
    }

    @Transactional
    public void updateImage(Long uid, byte[] imageData) {
        User user = userRepository.findById(uid).get();
        User new_user = new User(user.getUid(), user.getNickname(), user.getRole(), user.getCreateDate(), imageData);
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
    public byte[] findImageByUid(Long uid) {
        User user = userRepository.findById(uid).get();
        return user.getImageData();
    }

    @Transactional(readOnly = true)
    public boolean existNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public int countNickname(String nickname) {
        return userRepository.countByNicknameLike(nickname);
    }

}
