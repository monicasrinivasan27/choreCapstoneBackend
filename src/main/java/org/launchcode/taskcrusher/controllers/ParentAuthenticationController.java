package org.launchcode.taskcrusher.controllers;

import org.launchcode.taskcrusher.configure.UserAuthProvider;
import org.launchcode.taskcrusher.models.Kid;
import org.launchcode.taskcrusher.models.User;
import org.launchcode.taskcrusher.models.data.KidRepository;
import org.launchcode.taskcrusher.models.data.UserRepository;
import org.launchcode.taskcrusher.models.dto.*;
import org.launchcode.taskcrusher.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private final KidRepository kidRepository;

    @Autowired
    private final UserRepository userRepository;



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
    public ResponseEntity<KidUserDto> kidRegister(@RequestParam Long id, @RequestBody @Valid KidSignUpDto kidUserDto) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        System.out.println("Request Body: " + kidUserDto);
        KidUserDto createdKidUserDto = userService.kidRegister(kidUserDto);
        return ResponseEntity.created(URI.create("/users/" + createdKidUserDto.getId())).body(createdKidUserDto);
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