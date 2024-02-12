package org.launchcode.taskcrusher.controllers;

import org.launchcode.taskcrusher.configure.UserAuthProvider;
import org.launchcode.taskcrusher.exceptions.AccessException;
import org.launchcode.taskcrusher.models.Chore;
import org.launchcode.taskcrusher.models.data.ChoreRepository;
import org.launchcode.taskcrusher.models.dto.UserDto;
import org.launchcode.taskcrusher.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/chores")
@CrossOrigin
public class ChoreControllerApi {

    @Autowired
    private ChoreRepository choreRepository;

    @Autowired
    private UserAuthProvider userAuthProvider;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Iterable<Chore> getAllChores(Authentication authentication) {
        // Check if the user is authenticated
        Long userId = getUserIdFromAuthentication(authentication);
        if (userId != null) {
            return choreRepository.findAll();
        } else {
            throw new AccessException("User not authenticated");
        }
    }

    @PostMapping("/add")
    public String createChore(@RequestBody Chore chore, Authentication authentication) {
        // Check if the user is authenticated
        Long userId = getUserIdFromAuthentication(authentication);
        System.out.println("1234567");
        if (userId != null) {
            chore.getParent().setId(userId);
            choreRepository.save(chore);
            return "Chore created successfully";
        } else {
            throw new AccessException("User not authenticated");
        }
    }

    @GetMapping("/edit/{choreId}")
    public Object getChoreDetails(@PathVariable int choreId, Authentication authentication) {
        // Check if the user is authenticated
        Long userId = getUserIdFromAuthentication(authentication);
        if (userId != null) {
            Optional<Chore> optionalChore = choreRepository.findById(choreId);
            return optionalChore.orElse(null);
        } else {
            throw new AccessException("User not authenticated");
        }
    }

    @PutMapping("/edit/{choreId}")
    public Object updateChore(@PathVariable int choreId, @RequestBody Chore updatedChore, Authentication authentication) {
        // Check if the user is authenticated
        Long userId = getUserIdFromAuthentication(authentication);
        if (userId != null) {
            Optional<Chore> optionalChore = choreRepository.findById(choreId);
            // Check if the chore with the given ID exists
            if (optionalChore.isPresent()) {
                Chore existingChore = optionalChore.get();
                String imagePath = updatedChore.getImage();
                // Check the image path starts with "/images/"
                if (imagePath != null && !imagePath.startsWith("/images/")) {
                    imagePath = "/images/" + imagePath;
                }
                existingChore.setName(updatedChore.getName());
                existingChore.setDescription(updatedChore.getDescription());
                existingChore.setImage(imagePath);
                return choreRepository.save(existingChore);
            } else {
                return "Chore not found";
            }
        } else {
            throw new AccessException("User not authenticated");
        }
    }

    @DeleteMapping("/{choreId}")
    public void deleteChore(@PathVariable int choreId, Authentication authentication) {
        // Check if the user is authenticated
        Long userId = getUserIdFromAuthentication(authentication);
        if (userId != null) {
            choreRepository.deleteById(choreId);
        } else {
            throw new AccessException("User not authenticated");
        }
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        UserDto authenticatedUser = (UserDto) authentication.getPrincipal();
        return authenticatedUser != null ? authenticatedUser.getId() : null;
    }
}