package com.example.demo.entity;

import lombok.Data;

@Data
public class Message<T> {

    private MessageId id;
    private T msg;

    public Message(T msg)
    {
        this.msg = msg;
    }
}
