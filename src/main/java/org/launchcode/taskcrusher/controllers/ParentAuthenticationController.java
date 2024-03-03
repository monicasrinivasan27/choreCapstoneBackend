package org.launchcode.taskcrusher.controllers;

import org.launchcode.taskcrusher.configure.UserAuthProvider;
import org.launchcode.taskcrusher.models.data.UserRepository;
import org.launchcode.taskcrusher.models.dto.*;
import org.launchcode.taskcrusher.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RequiredArgsConstructor
@RestController
public class ParentAuthenticationController {

    private static final Logger logger = Logger.getLogger(ParentAuthenticationController.class.getName());

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;



    @PostMapping("/api/parentLogin")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        String token = userAuthProvider.createToken(userDto);
        userDto.setToken(token);
        logger.info(token);

        return ResponseEntity.ok()

                .body(userDto);
    }

    @PostMapping("/api/kidLogin")
    public ResponseEntity<KidUserDto> kidLogin(@RequestBody @Valid CredentialsDto credentialsDto) {
        KidUserDto kidUserDto = userService.kidLogin(credentialsDto);
        String token = userAuthProvider.createKidToken(kidUserDto);
        kidUserDto.setToken(token);
        logger.info(token);

        return ResponseEntity.ok().body(kidUserDto);
    }

    @PostMapping("/api/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
        logger.info("Received a request: {}");
        System.out.println("Received registration request for user: " + user);
        UserDto createUser = userService.register(user);
//        createUser.setToken(userAuthProvider.createToken(createUser));
        return ResponseEntity.created(URI.create("/users/" + createUser.getId())).body(createUser);
    }

    @PostMapping("/api/kidRegister")
    public ResponseEntity<KidUserDto> kidRegister(@RequestBody @Valid KidSignUpDto kidUser) {
        logger.info("Received a request: {}");
        System.out.println("Received a kid registration request for kid user: " + kidUser);
        KidUserDto createKidUser = userService.kidRegister(kidUser);

        return ResponseEntity.created(URI.create("/users/" + createKidUser.getId())).body(createKidUser);
    }

    @PostMapping("/api/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/api/logout-success")
    public String logoutSuccess() {

        return "Logout successful!";
    }



}