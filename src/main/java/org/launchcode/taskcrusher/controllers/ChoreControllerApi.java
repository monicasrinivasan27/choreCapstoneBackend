package org.launchcode.taskcrusher.controllers;

import org.launchcode.taskcrusher.models.Chore;
import org.launchcode.taskcrusher.models.User;
import org.launchcode.taskcrusher.models.data.ChoreRepository;
import org.launchcode.taskcrusher.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/chores")
@CrossOrigin
public class ChoreControllerApi {

    @Autowired
    private ChoreRepository choreRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/list")
    public Iterable<Chore> getChoresForParent(@RequestParam Long id) {
        Optional<User> optionalParent = userRepository.findById(id);

        if (optionalParent.isPresent()) {
            User parent = optionalParent.get();
            return choreRepository.findByParent(parent);
        } else {

            return Collections.emptyList();
        }
    }

    // Fetch details of an existing chore for editing
    @GetMapping("/edit/{choreId}")
    public Object getChoreDetailsForEditing(
            @PathVariable int choreId,
            @RequestParam Long id) {

        Optional<Chore> optionalChore = choreRepository.findById(choreId);

        if (optionalChore.isPresent()) {
            Chore chore = optionalChore.get();

            // Check if the chore belongs to the specified parent
            if (chore.getParent().getId().equals(id)) {
                // Return the chore details if the parent is authorized to edit
                return chore;
            } else {
                // Return a message if the chore does not belong to the specified parent
                return "parent not found";
            }
        } else {
            // Return a message if the chore is not found
            return "Chore not found";
        }
    }


    // Add/create a new chore for a specific parent
    @PostMapping("/add")
    public Chore createChoreForParent(@RequestParam Long id, @RequestBody Chore chore) {
        Optional<User> optionalParent = userRepository.findById(id);

        if (optionalParent.isPresent()) {
            User parent = optionalParent.get();
            chore.setParent(parent);
            return choreRepository.save(chore);
        } else {
            return null;
        }
    }

    // Update an existing chore for a specific parent

    @PutMapping("/edit/{choreId}")
    public Chore updateChoreForParent(@PathVariable int choreId, @RequestParam Long id, @RequestBody Chore updatedChore) {
        Optional<User> optionalParent = userRepository.findById(id);

        if (optionalParent.isPresent()) {
            User parent = optionalParent.get();
            Optional<Chore> optionalChore = choreRepository.findById(choreId);

            if (optionalChore.isPresent()) {
                Chore existingChore = optionalChore.get();
                String imagePath = updatedChore.getImage();

                // Ensure the image path starts with "/images/"
                if (imagePath != null && !imagePath.startsWith("/images/")) {
                    imagePath = "/images/" + imagePath;
                }
                existingChore.setName(updatedChore.getName());
                existingChore.setDescription(updatedChore.getDescription());
                existingChore.setImage(updatedChore.getImage());
                existingChore.setParent(parent);
                existingChore.setImage(imagePath);
                return choreRepository.save(existingChore);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    // Delete a chore based on its ID
    @DeleteMapping("/{choreId}")
    public void deleteChore(@PathVariable int choreId) {
        choreRepository.deleteById(choreId);
    }
}
