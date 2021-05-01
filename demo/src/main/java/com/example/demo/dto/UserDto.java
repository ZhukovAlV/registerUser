package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private Integer id;
    private String login;
    private String password;
    private String email;
    private String name;
    private String surname;
    private String patronymic;
}
