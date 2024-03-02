package org.launchcode.taskcrusher.controllers;

import org.launchcode.taskcrusher.models.data.KidRepository;
import org.launchcode.taskcrusher.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CheckUniqueController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KidRepository kidRepository;


    @PostMapping("/api/checkUnique")
    public Map<String, Object> parentCheckUnique(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String email = request.get("email");

        boolean usernameExists = userRepository.existsByUsername(username);
        boolean emailExists = userRepository.existsByEmail(email);

        Map<String, Object> response = new HashMap<>();
        response.put("usernameExists", usernameExists);
        response.put("emailExists", emailExists);

        return response;
    }

    @PostMapping("/api/checkKidUnique")
    public Map<String, Object> kidCheckUnique(@RequestBody Map<String, String> request) {
        String username = request.get("username");

        boolean usernameExists = kidRepository.existsByUsername(username);

        Map<String, Object> response = new HashMap<>();
        response.put("usernameExists", usernameExists);

        return response;
    }


}
