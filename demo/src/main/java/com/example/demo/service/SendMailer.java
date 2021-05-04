package com.example.demo.service;

import com.example.demo.entity.EmailAddress;
import com.example.demo.entity.EmailContent;

import java.util.concurrent.TimeoutException;

/**
 * Ориентировочный интерфейс мейлера.
 */
public interface SendMailer {
    void sendMail (EmailAddress toAddress, EmailContent messageBody) throws TimeoutException;
}
