package org.launchcode.taskcrusher.controllers;


import org.launchcode.taskcrusher.enums.ChoreStatus;
import org.launchcode.taskcrusher.enums.ChoreValueType;
import org.launchcode.taskcrusher.models.Chore;
import org.launchcode.taskcrusher.models.Kid;
import org.launchcode.taskcrusher.models.data.ChoreRepository;
import org.launchcode.taskcrusher.models.data.KidRepository;
import org.launchcode.taskcrusher.models.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin
public class StatusControllerApi {

    @Autowired
    private ChoreRepository choreRepository;

    @Autowired
    private KidRepository kidRepository;

    // Endpoint for a parent to approve a completed chore and set points/dollars
    @PostMapping("/approve/{choreId}")
    public String approveChore(@PathVariable int choreId, Authentication authentication) {
        Optional<Chore> choreOptional = choreRepository.findById(choreId);

        if (!choreOptional.isPresent()) {
            return "Chore not found";
        }

        Chore chore = choreOptional.get();

        // Check if the authenticated user is the parent of the kid associated with the chore
        UserDto authenticatedUser = (UserDto) authentication.getPrincipal();
        Kid kid = chore.getKid();

        if (kid == null || !authenticatedUser.getId().equals(kid.getParent().getId())) {
            return "User is not authorized to approve this chore.";
        }

        // Logic to check if the chore can be approved
        if (!ChoreStatus.COMPLETED.equals(chore.getStatus())) {
            return "Chore cannot be approved. Invalid status.";
        }

        int currentValue = kid.getPoints();
        int choreValue = chore.getValue();

        // Using ChoreValueType enum directly
        ChoreValueType valueType = chore.getValueType();

        if (ChoreValueType.POINTS.equals(valueType)) {
            kid.setPoints(currentValue + choreValue);
        } else if (ChoreValueType.DOLLARS.equals(valueType)) {

            kid.setDollars(currentValue + choreValue);
        } else {
            return "Invalid value type for chore. Should be 'POINTS' or 'DOLLARS'.";
        }

        kidRepository.save(kid);

        // Update the chore status to APPROVED
        chore.setStatus(ChoreStatus.APPROVED);
        choreRepository.save(chore);

        return "Chore approved and points/dollars set for kid";
    }

}