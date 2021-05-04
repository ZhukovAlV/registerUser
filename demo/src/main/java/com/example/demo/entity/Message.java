package com.example.demo.entity;

import com.example.demo.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message<T> {

    private MessageId id;
    private T user;
}
