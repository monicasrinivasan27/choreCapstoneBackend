package org.launchcode.taskcrusher.service;

import org.launchcode.taskcrusher.models.dto.CredentialsDto;
import org.launchcode.taskcrusher.models.dto.SignUpDto;
import org.launchcode.taskcrusher.models.dto.UserDto;
import org.launchcode.taskcrusher.models.User;
import org.launchcode.taskcrusher.exceptions.AppException;
import org.launchcode.taskcrusher.mappers.UserMapper;
import org.launchcode.taskcrusher.models.data.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByUsername(credentialsDto.username())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto userDto) {
        System.out.println(11111);
        Optional<User> optionalUser = userRepository.findByUsername(userDto.username());
        System.out.println(222222);
        if (optionalUser.isPresent()) {
            throw new AppException("Username already exists", HttpStatus.BAD_REQUEST);
        }
        ;
        System.out.println("the user dto is" + userDto.toString());
        User user = userMapper.signUpToUser(userDto);
        System.out.println("the user object is" + user.toString());
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.password())));

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);

    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }




}