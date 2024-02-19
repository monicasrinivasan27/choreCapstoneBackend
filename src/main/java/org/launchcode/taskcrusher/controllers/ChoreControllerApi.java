package org.launchcode.taskcrusher.controllers;

import org.launchcode.taskcrusher.models.Chore;
import org.launchcode.taskcrusher.models.data.ChoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/chores") // Base URL mapping for all endpoints in this controller
@CrossOrigin // Enable cross-origin resource sharing to allow requests from different origins
public class ChoreControllerApi {

    // Autowire the ChoreRepository for interacting with chore data in the database
    @Autowired
    private ChoreRepository choreRepository;

    // Retrieve a list of all chores
    @GetMapping("/list")
    public Iterable<Chore> getAllChores() {
        return choreRepository.findAll(); // Fetch all chores from the database and return them
    }

    // Add/create a new chore
    @PostMapping("/add")
    public Chore createChore(@RequestBody Chore chore) {
        return choreRepository.save(chore); // Save the new chore to the database and return the saved chore
    }

    // Fetch details of an existing chore for editing
    @GetMapping("/edit/{choreId}")
    public Object getChoreDetails(@PathVariable int choreId) {
        Optional<Chore> optionalChore = choreRepository.findById(choreId);

        // Check if the chore with the given ID exists
        if (optionalChore.isPresent()) {
            return optionalChore.get(); // Return the chore details if found
        } else {
            return "Chore not found"; // Return a message if the chore is not found
        }
    }

    // Update an existing chore
    @PutMapping("/edit/{choreId}")
    public Object updateChore(@PathVariable int choreId, @RequestBody Chore updatedChore) {
        Optional<Chore> optionalChore = choreRepository.findById(choreId);

        // Check if the chore with the given ID exists
        if (optionalChore.isPresent()) {
            Chore existingChore = optionalChore.get();
            String imagePath = updatedChore.getImage();

            // Ensure the image path starts with "/images/"
            if (imagePath != null && !imagePath.startsWith("/images/")) {
                imagePath = "/images/" + imagePath;
            }

            // Update chore details with the new information
            existingChore.setName(updatedChore.getName());
            existingChore.setDescription(updatedChore.getDescription());
            existingChore.setImage(imagePath);

            return choreRepository.save(existingChore); // Save the updated chore to the database and return it
        } else {
            return "Chore not found"; // Return a message if the chore is not found
        }
    }

    // Delete a chore based on its ID
    @DeleteMapping("/{choreId}")
    public void deleteChore(@PathVariable int choreId) {
        choreRepository.deleteById(choreId); // Delete the chore from the database based on its ID
    }
}