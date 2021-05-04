package com.example.demo.service;

import com.example.demo.dto.UserDto;
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

@Service
@AllArgsConstructor
public class UserServiceStub implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final MessagingServiceStub messagingService;

    @Override
    @Transactional
    public UserDto saveUser(@NotNull UserDto userDto) throws ValidationException, TimeoutException {
        validateUserDto(userDto);
        User convertedUser = userConverter.fromUserDtoToUser(userDto);
        User savedUser = userRepository.save(convertedUser);
        messagingService.doRequest(new Message<>(userDto));
        return userConverter.fromUserToUserDto(savedUser);
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
    }
}
