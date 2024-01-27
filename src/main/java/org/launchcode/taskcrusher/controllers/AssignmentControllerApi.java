package org.launchcode.taskcrusher.controllers;

import org.launchcode.taskcrusher.models.dto.AssignedChoresDTO;
import org.launchcode.taskcrusher.models.Chore;
import org.launchcode.taskcrusher.models.Kid;
import org.launchcode.taskcrusher.models.data.ChoreRepository;
import org.launchcode.taskcrusher.models.data.KidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/assignments")
public class AssignmentControllerApi {

    // Injecting ChoreRepository and KidRepository for database operations
    @Autowired
    private ChoreRepository choreRepository;

    @Autowired
    private KidRepository kidRepository;

    // Retrieving all kids from the database
    @GetMapping("/kids")
    public Iterable<Kid> getAllKids() {
        return kidRepository.findAll();
    }


    // Assigning chores to kids with due date, value, and value type
    @PostMapping("/{choreId}/{kidId}")
    public String assignChoreToKid(
            @PathVariable int choreId,
            @PathVariable int kidId,
            @RequestParam(name = "dueDate") LocalDate dueDate,
            @RequestParam(name = "value") int value,
            @RequestParam(name = "valueType") String valueType) {

        // Checking if both the chore and kid exist in the database
        Optional<Chore> choreOptional = choreRepository.findById(choreId);
        Optional<Kid> kidOptional = kidRepository.findById(kidId);

        if (choreOptional.isPresent() && kidOptional.isPresent()) {
            Chore chore = choreOptional.get();
            Kid kid = kidOptional.get();

            // Assigning values to the Chore object
            chore.setKid(kid);
            chore.setDueDate(dueDate);
            chore.setValueType(valueType);
            chore.setValue(value);

            // Saving the updated Chore in the database
            choreRepository.save(chore);

            return "Chore assigned successfully";
        } else {
            return "Chore or Kid not found";
        }
    }

    
}