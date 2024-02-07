package org.launchcode.taskcrusher.controllers;

import org.launchcode.taskcrusher.config.UserAuthProvider;
import org.launchcode.taskcrusher.dto.CredentialsDto;
import org.launchcode.taskcrusher.dto.SignUpDto;
import org.launchcode.taskcrusher.dto.UserDto;
import org.launchcode.taskcrusher.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class ParentAuthenticationController {

//----------------FROM JWT VIDEO-----------------------------------------------
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
        UserDto createUser = userService.register(user);
//        createUser.setToken(userAuthProvider.createToken(createUser));
        return ResponseEntity.created(URI.create("/parentLogin" + createUser.getId())).body(createUser);
    }
//------------------------------------------------------------------------------

//    //Handler for logging out
//    @GetMapping("/api/parent-logout")
//    public String parentLogout(HttpServletRequest request) {
//        request.getSession().invalidate();
//        return "redirect:/api/parent-login";
//    }

}
