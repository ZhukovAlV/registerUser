package com.example.demo.service;

import com.example.demo.entity.Message;
import com.example.demo.entity.MessageId;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class MessagingServiceStub implements MessagingService {

    @Override
    public <T> MessageId send(Message<T> msg) {
        msg.setId(new MessageId(UUID.randomUUID()));
        // Здесь получается должна быть в будущем прописана отправка сообщения во внешнюю систему
        return msg.getId();
    }

    @Override
    public <T> Message<T> receive(MessageId messageId) throws TimeoutException {
        if(shouldThrowTimeout()) {
            sleep();

            throw new TimeoutException("Timeout!");
        }

        if(shouldSleep()) {
            sleep();
        }

        // return our stub message here.
        return null;
    }

    @Override
    public <R, A> Message<A> doRequest(Message<R> request) throws TimeoutException {
        final MessageId messageId = send(request);

        return receive(messageId);
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
