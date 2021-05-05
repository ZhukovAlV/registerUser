package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.EmailAddress;
import com.example.demo.entity.EmailContent;
import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class UserServiceStub implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final MessagingServiceStub messagingService;
    private final SendMailerStub sendMailerStub;

    @Override
    @Transactional
    public UserDto saveUser(@NotNull UserDto userDto) throws ValidationException, TimeoutException {
        // Проверяем введенные данные на корректность
        validateUserDto(userDto);

        // Отправляем данные во внешнюю систему на проверку и ждем ответа
        if (nonNull(messagingService.doRequest(new Message<>(userDto)))) {
            // Если все успешно создаем пользователя и сохраняем в БД
            User convertedUser = userConverter.fromUserDtoToUser(userDto);
            User savedUser = userRepository.save(convertedUser);
            // И отправляем письмо пользователю
            EmailAddress toAddress = new EmailAddress(userDto.getEmail());
            EmailContent messageBody = new EmailContent("Поздравляем с успешной регистрацией");
            sendMailerStub.sendMail(toAddress, messageBody);
            return userConverter.fromUserToUserDto(savedUser);
        }
        // В случае не успешной проверки ничего не возращаем
        return null;
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto findByLogin(String login) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            return userConverter.fromUserToUserDto(user);
        }
        return null;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userConverter::fromUserToUserDto)
                .collect(Collectors.toList());
    }

    private void validateUserDto(UserDto userDto) throws ValidationException {
        if (isNull(userDto)) {
            throw new ValidationException("Object user is null");
        }
        if (isNull(userDto.getLogin()) || userDto.getLogin().isEmpty()) {
            throw new ValidationException("Login is empty");
        }
        if (nonNull(userRepository.findByLogin(userDto.getLogin()))) {
            throw new ValidationException("Login already exists");
        }
        if (isNull(userDto.getName()) || isNull(userDto.getSurname())) {
            throw new ValidationException("Name or Surname is empty");
        }
        if (isNull(userDto.getEmail())) {
            throw new ValidationException("Email is empty");
        }
        if (!userDto.getEmail().matches("[\\w\\s\\d]+@([\\w\\s\\d\\u002E])((ru)|(com))")) {
            throw new ValidationException("Email is not correct");
        }
    }
}
