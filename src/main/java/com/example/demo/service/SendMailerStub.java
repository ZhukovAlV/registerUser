package com.example.demo.service;

import com.example.demo.entity.EmailAddress;
import com.example.demo.entity.EmailContent;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Log4j2
public class SendMailerStub implements SendMailer {

    @Override
    public void sendMail(EmailAddress toAddress, EmailContent messageBody) throws TimeoutException {
        if(shouldThrowTimeout()) {
            sleep();

            throw new TimeoutException("Timeout!");
        }

        if(shouldSleep()) {
            sleep();
        }

        // ok.
        log.info("Message sent to {}, body {}.", toAddress, messageBody);
    }

    @SneakyThrows
    private static void sleep() {
        Thread.sleep(TimeUnit.MINUTES.toMillis(1));
    }

    private static boolean shouldSleep() {
        return new Random().nextInt(10) == 1;
    }

    private static boolean shouldThrowTimeout() {
        return new Random().nextInt(10) == 1;
    }
}