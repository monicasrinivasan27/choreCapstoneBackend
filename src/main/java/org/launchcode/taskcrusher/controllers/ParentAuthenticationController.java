package org.launchcode.taskcrusher.controllers;

import org.launchcode.taskcrusher.configure.UserAuthProvider;
import org.launchcode.taskcrusher.models.dto.*;
import org.launchcode.taskcrusher.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import java.net.URI;
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
        userDto.setToken(userAuthProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/api/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
        logger.info("Received a request: {}");
        System.out.println("Received registration request for user: " + user);
        UserDto createUser = userService.register(user);
        createUser.setToken(userAuthProvider.createToken(createUser));
        return ResponseEntity.created(URI.create("/users/" + createUser.getId())).body(createUser);
    }

    @PostMapping("api/kidRegister")
    public ResponseEntity<KidUserDto> addKidUser(@RequestBody @Valid KidSignUpDto kidUser) {
        KidUserDto createKidUser = userService.kidRegister(kidUser);
        createKidUser.setToken(userAuthProvider.createKidToken(createKidUser));
        return ResponseEntity.created(URI.create("/kidUsers/" + createKidUser.getId())).body(createKidUser);
    }

//    //Handler for logging out
//    @GetMapping("/api/parent-logout")
//    public String parentLogout(HttpServletRequest request) {
//        request.getSession().invalidate();
//        return "redirect:/api/parent-login";
//    }

}