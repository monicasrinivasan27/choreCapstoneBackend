package org.launchcode.taskcrusher.controllers;

import org.launchcode.taskcrusher.enums.ChoreStatus;
import org.launchcode.taskcrusher.models.Chore;
import org.launchcode.taskcrusher.models.Kid;
import org.launchcode.taskcrusher.models.data.ChoreRepository;
import org.launchcode.taskcrusher.models.data.KidRepository;
import org.launchcode.taskcrusher.models.dto.AssignedChoresDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

            //set choreStatus to assigned
            chore.setStatus(ChoreStatus.ASSIGNED);

            // Saving the updated Chore in the database
            choreRepository.save(chore);

            return "Chore assigned successfully";
        } else {
            return "Chore or Kid not found";
        }
    }

    // Get the list of assigned chores for kids
    @GetMapping("/assigned-chores")
    public List<AssignedChoresDTO> viewAssignedChores() {
        // Retrieve all kids from the database
        Iterable<Kid> kids = kidRepository.findAll();
        // Create a list to store the result (assigned chores for each kid)
        List<AssignedChoresDTO> result = new ArrayList<>();

        // Iterate through each kid to find their assigned chores
        for (Kid kid : kids) {
            // Find the chores assigned to the current kid
            List<Chore> assignedChores = choreRepository.findByKid(kid);

            // Group the chores based on their status
            Map<String, List<Chore>> groupedChores = new HashMap<>();
            for (Chore chore : assignedChores) {
                String status = chore.getStatus().name();

                // Create a list for the status if it doesn't exist
                List<Chore> choresForStatus = groupedChores.computeIfAbsent(status, k -> new ArrayList<>());
                // Add the chore to the list for the status
                choresForStatus.add(chore);
            }

            // Create a DTO to store the kid and their assigned chores
            AssignedChoresDTO assignedChoresDTO = new AssignedChoresDTO();
            assignedChoresDTO.setKid(kid);
            assignedChoresDTO.setChores(assignedChores);

            // Add the DTO to the result list
            result.add(assignedChoresDTO);
        }

        // Return the list of assigned chores for all kids
        return result;
    }


    // Delete an assigned chore
    @DeleteMapping("/assigned-chores/{choreId}")
    public String deleteAssignedChore(@PathVariable int choreId) {
        Optional<Chore> choreOptional = choreRepository.findById(choreId);

        if (choreOptional.isPresent()) {
            Chore choreToDelete = choreOptional.get();

            // Check if the chore is in not completed before deleting
            if (choreToDelete.getStatus() != ChoreStatus.COMPLETED) {
                // Delete the assigned chore
                choreRepository.delete(choreToDelete);
                return "Assigned chore deleted successfully";
            } else {
                return "Cannot delete completed chores";
            }
        } else {
            return "Chore not found";
        }
    }


}