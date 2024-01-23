package org.launchcode.taskcrusher.controllers;

import org.launchcode.taskcrusher.models.Chore;
import org.launchcode.taskcrusher.models.data.ChoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/chores")
@CrossOrigin
public class ChoreControllerApi {

    @Autowired
    private ChoreRepository choreRepository;

    @GetMapping("/list")
    public Iterable<Chore> getAllChores() {
        return choreRepository.findAll();
    }

    @PostMapping("/add")
    public Chore createChore(@RequestBody Chore chore) {
        return choreRepository.save(chore);
    }

    @GetMapping("/edit/{choreId}")
    public Object getChoreDetails(@PathVariable int choreId) {
        Optional<Chore> optionalChore = choreRepository.findById(choreId);

        if (optionalChore.isPresent()) {
            return optionalChore.get();
        } else {
            return "Chore not found";
        }
    }

    @PutMapping("/edit/{choreId}")
    public Object updateChore(@PathVariable int choreId, @RequestBody Chore updatedChore) {
        Optional<Chore> optionalChore = choreRepository.findById(choreId);

        if (optionalChore.isPresent()) {
            Chore existingChore = optionalChore.get();
            String imagePath = updatedChore.getImage();

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
    }

    @DeleteMapping("/{choreId}")
    public void deleteChore(@PathVariable int choreId) {
        choreRepository.deleteById(choreId);
    }
}
