package com.health.springbootback.service;

import com.health.springbootback.dto.UserListDto;
import com.health.springbootback.model.UserList;
import com.health.springbootback.repository.BestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RankingService {
    @Autowired
    private BestRepository bestRepository;

    @Transactional
    public List<UserListDto> findUserRanking(String c, String subCategory) {
        List<UserList> userList;
        String category = "";
        if(Objects.equals(c, "4-major"))      category = "FOURMAJOR";
        if(Objects.equals(c, "freestyle"))    category = "FREESTYLE";
        if(Objects.equals(c, "bare-body"))    category = "BAREBODY";

        if(Objects.equals(subCategory, "SUM"))
            userList = bestRepository.findByCategory(category);
        else
            userList = bestRepository.findByCategoryAndSubC(category, subCategory);

        List<UserListDto> userListDtoList = userList.stream()
                .map(ul -> {
                    String nickname = ul.getNickname();
                    float b_sum = ul.getB_sum();
                    String most = ul.getMost();

                    return new UserListDto(nickname, b_sum, most);
                }) .collect(Collectors.toList());
        System.out.println(userListDtoList);
        return userListDtoList;
    }
}
