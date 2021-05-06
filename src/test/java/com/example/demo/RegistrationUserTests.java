package com.example.demo;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.Message;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MessagingServiceStub;
import com.example.demo.service.SendMailerStub;
import com.example.demo.service.UserConverter;
import com.example.demo.service.UserServiceStub;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class RegistrationUserTests {

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

    // проверяем корректный логин
    @Test
    public void testCorrectLogin() throws TimeoutException, ValidationException {
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

        UserDto userRes = userServiceStub.saveUser(userDto);
        Assert.assertEquals(userDto.getLogin(),userRes.getLogin());
    }

    // проверяем некорректный логин
    @Test(expected = javax.xml.bind.ValidationException.class)
    public void testNotCorrectLogin() throws TimeoutException, ValidationException {
        UserDto userDto = UserDto.builder()
                .login("")
                .password("password")
                .email("email@email.com")
                .surname("Surname")
                .name("Name")
                .patronymic("Patronymic")
                .build();

        UserDto UserRes = userServiceStub.saveUser(userDto);
    }


    // проверяем некорректный пароль
    @Test(expected = javax.xml.bind.ValidationException.class)
    public void testNotCorrectPassword() throws TimeoutException, ValidationException {
        UserDto userDto = UserDto.builder()
                .login("login")
                .password("")
                .email("email@email.com")
                .surname("Surname")
                .name("Name")
                .patronymic("Patronymic")
                .build();

        UserDto UserRes = userServiceStub.saveUser(userDto);
    }

    // проверяем корректный Email
    @Test
    public void testCorrectEmail() throws TimeoutException, ValidationException {
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

        UserDto userRes = userServiceStub.saveUser(userDto);
        Assert.assertEquals(userDto.getEmail(),userRes.getEmail());
    }

    // проверяем некорректный Email
    @Test(expected = javax.xml.bind.ValidationException.class)
    public void testNotCorrectEmail() throws TimeoutException, ValidationException {
        UserDto userDto = UserDto.builder()
                .login("login")
                .password("password")
                .email("tra-ta-ta")
                .surname("Surname")
                .name("Name")
                .patronymic("Patronymic")
                .build();

        UserDto UserRes = userServiceStub.saveUser(userDto);
    }

    // проверяем корректное имя
    @Test
    public void testCorrectName() throws TimeoutException, ValidationException {
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

        UserDto userRes = userServiceStub.saveUser(userDto);
        Assert.assertEquals(userDto.getName(),userRes.getName());
    }

    // проверяем некорректное имя
    @Test(expected = javax.xml.bind.ValidationException.class)
    public void testNotCorrectName() throws TimeoutException, ValidationException {
        UserDto userDto = UserDto.builder()
                .login("login")
                .password("password")
                .email("email@email.com")
                .surname("Surname")
                .name("")
                .patronymic("Patronymic")
                .build();

        UserDto UserRes = userServiceStub.saveUser(userDto);
    }

    // проверяем корректную фамилию
    @Test
    public void testCorrectSurname() throws TimeoutException, ValidationException {
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

        UserDto userRes = userServiceStub.saveUser(userDto);
        Assert.assertEquals(userDto.getSurname(),userRes.getSurname());
    }

    // проверяем некорректную фамилию
    @Test(expected = javax.xml.bind.ValidationException.class)
    public void testNotCorrectSurname() throws TimeoutException, ValidationException {
        UserDto userDto = UserDto.builder()
                .login("login")
                .password("password")
                .email("email@email.com")
                .surname("")
                .name("Name")
                .patronymic("Patronymic")
                .build();

        UserDto UserRes = userServiceStub.saveUser(userDto);
    }


    // проверяем корректное отчество
    @Test
    public void testCorrectPatronymic() throws TimeoutException, ValidationException {
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

        UserDto userRes = userServiceStub.saveUser(userDto);
        Assert.assertEquals(userDto.getPatronymic(),userRes.getPatronymic());
    }
}
