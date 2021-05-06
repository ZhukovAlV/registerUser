package com.example.demo;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.*;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MessagingServiceStub;
import com.example.demo.service.SendMailerStub;
import com.example.demo.service.UserConverter;
import com.example.demo.service.UserServiceStub;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.ValidationException;
import java.util.concurrent.TimeoutException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageServiceTest {

    @Autowired
    private UserServiceStub userServiceStub;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private SendMailerStub sendMailerStub;

    @MockBean
    private MessagingServiceStub messagingServiceStub;

    // проверяем отправку сообщения на E-mail
    @Test
    public void testMessageService() throws TimeoutException, ValidationException {
        UserDto userDto = UserDto.builder()
                .login("login")
                .password("password")
                .email("email@email.com")
                .surname("Surname")
                .name("Name")
                .patronymic("Patronymic")
                .build();

        when(messagingServiceStub.doRequest(any(Message.class))).thenReturn(new Message<>(userDto));

        // удаляем пользователя если он уже есть в базе
        if (userServiceStub.findByLogin(userDto.getLogin()) != null) {
            userServiceStub.deleteUser(userServiceStub.findByLogin(userDto.getLogin()).getId());
        }

        EmailAddress emailAddress = new EmailAddress(userDto.getEmail());
        EmailContent emailContent = new EmailContent("Поздравляем с успешной регистрацией");

        UserDto userRes = userServiceStub.saveUser(userDto);
        Assert.assertNotNull(userRes);

        Mockito.verify(sendMailerStub, Mockito.times(1))
                .sendMail(
                        ArgumentMatchers.eq(emailAddress),
                        ArgumentMatchers.eq(emailContent)
                );
    }
}

