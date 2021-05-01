package com.example.demo.service;

import com.example.demo.dto.UserDto;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface UserService {

    UserDto saveUser(UserDto usersDto) throws ValidationException;

    void deleteUser(Integer userId);

    UserDto findByLogin(String login);

    List<UserDto> findAll();
}
