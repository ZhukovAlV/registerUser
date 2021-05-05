package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User fromUserDtoToUser(UserDto usersDto) {
        User user = new User();
        user.setId(usersDto.getId());
        user.setLogin(usersDto.getLogin());
        user.setPassword(usersDto.getPassword());
        user.setEmail(usersDto.getEmail());
        user.setName(usersDto.getName());
        user.setSurname(usersDto.getSurname());
        user.setPatronymic(usersDto.getPatronymic());
        return user;
    }

    public UserDto fromUserToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .patronymic(user.getPatronymic())
                .build();
    }
}
