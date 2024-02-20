package org.launchcode.taskcrusher.controllers;

import org.launchcode.taskcrusher.enums.ChoreStatus;
import org.launchcode.taskcrusher.models.Chore;
import org.launchcode.taskcrusher.models.Kid;
import org.launchcode.taskcrusher.models.data.ChoreRepository;
import org.launchcode.taskcrusher.models.data.KidRepository;
import org.launchcode.taskcrusher.models.dto.AssignedChoresDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chores")
@CrossOrigin("http://localhost:3000")
public class KidsAssignedChore {
    @Autowired
    private KidRepository kidRepository;

    @Autowired
    private ChoreRepository choreRepository;

    @GetMapping("/kids-assigned-chores/{kidId}")
    public ResponseEntity<AssignedChoresDTO> getAssignedChoresForKid(@PathVariable int kidId) {
        Optional<Kid> kidOptional = kidRepository.findById(kidId);

        if (kidOptional.isPresent()) {
            Kid kid = kidOptional.get();
            List<Chore> assignedChores = choreRepository.findByKid(kid);

            AssignedChoresDTO assignedChoresDTO = new AssignedChoresDTO();
            assignedChoresDTO.setKid(kid);
            assignedChoresDTO.setChores(assignedChores);
            return new ResponseEntity<>(assignedChoresDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/kid-complete/{kidId}/{choreId}")
    public ResponseEntity<String> markChoreAsComplete(@PathVariable int kidId, @PathVariable int choreId) {
        Optional<Kid> kidOptional = kidRepository.findById(kidId);

        if (kidOptional.isPresent()) {
            Kid kid = kidOptional.get();
            Optional<Chore> choreOptional = choreRepository.findById(choreId);

            if (choreOptional.isPresent()) {
                Chore chore = choreOptional.get();

                // Check if the chore is assigned to the specified kid
                if (chore.getKid().equals(kid)) {
                    // Mark the chore as completed
                    chore.setStatus(ChoreStatus.COMPLETED);
                    choreRepository.save(chore);

                    return new ResponseEntity<>("Chore marked as completed successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Chore is not assigned to the specified kid", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Chore not found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Kid not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/kids-assigned-chores-by-username/{userName}")
    public ResponseEntity<AssignedChoresDTO> getAssignedChoresForKidNyUsername(@PathVariable String userName) {
        Optional<Kid> kidOptional = kidRepository.findByUsername(userName);
        if (kidOptional.isPresent()) {
            Kid kid = kidOptional.get();
            List<Chore> assignedChores = choreRepository.findByKid(kid);

            AssignedChoresDTO assignedChoresDTO = new AssignedChoresDTO();
            assignedChoresDTO.setKid(kid);
            assignedChoresDTO.setChores(assignedChores);
            return new ResponseEntity<>(assignedChoresDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

