package org.launchcode.taskcrusher.service;

import org.launchcode.taskcrusher.models.Kid;
import org.launchcode.taskcrusher.models.data.KidRepository;
import org.launchcode.taskcrusher.models.dto.*;
//import org.launchcode.taskcrusher.models.dto.KidUserDto;
import org.launchcode.taskcrusher.models.User;
import org.launchcode.taskcrusher.exceptions.AppException;
import org.launchcode.taskcrusher.mappers.UserMapper;
import org.launchcode.taskcrusher.models.data.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.ap.internal.model.common.FinalField;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final KidRepository kidRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByUsername(credentialsDto.username())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        Kid kidUser = kidRepository.findByUsername(credentialsDto.username())
                .orElseThrow(() -> new AppException("Unknown kid user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
//        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);

//        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), kidUser.getPassword())) {
//            return userMapper.toKidUserDto(kidUser);
//        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    //------------------------------ questions about how to return savedKidUser-----------------------------
    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByUsername(userDto.username());
//        Optional<Kid> optionalKidUSer = kidRepository.findByUsername(userDto.username());
        if (optionalUser.isPresent()/* || optionalKidUSer.isPresent()*/) {
            throw new AppException("Username already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.password())));
        User savedUser = userRepository.save(user);

//        Kid kidUser = userMapper.signUpToUser(userDto);
//        kidUser.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.password())));
//        Kid savedKidUser = userRepository.save(kidUser);

        return userMapper.toUserDto(savedUser);
    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    //------------------------------------------------------------------------------------------------------

//-----------------------------PARENT SERVICE (above) KIDS SERVICE (below)-----------------------------------------

    public KidUserDto kidLogin(CredentialsDto credentialsDto) {
        Kid kidUser = kidRepository.findByUsername(credentialsDto.username())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), kidUser.getPassword())) {
            return userMapper.toKidUserDto(kidUser);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public KidUserDto kidRegister(KidSignUpDto kidUserDto) {
        Optional<Kid> optionalKidUser = kidRepository.findByUsername(kidUserDto.username());
        if (optionalKidUser.isPresent()) {
            throw new AppException("Username already exists", HttpStatus.BAD_REQUEST);
        }
        Kid kidUser = userMapper.signUpToKidUser(kidUserDto);
        kidUser.setPassword(passwordEncoder.encode(CharBuffer.wrap(kidUserDto.password())));

        Kid savedKidUser = kidRepository.save(kidUser);

        return userMapper.toKidUserDto(savedKidUser);

    }

    public KidUserDto findByKidUsername(String username) {
        Kid kidUser = kidRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toKidUserDto(kidUser);
    }

}