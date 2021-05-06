package com.example.demo.service;

import com.example.demo.entity.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageListenerStub implements MessageListener {
    @Override
    public void handleMessage(Message incomingMessage) {

    }
}
