package org.launchcode.taskcrusher.controllers;


import org.launchcode.taskcrusher.enums.ChoreStatus;
import org.launchcode.taskcrusher.models.Chore;
import org.launchcode.taskcrusher.models.Kid;
import org.launchcode.taskcrusher.models.data.ChoreRepository;
import org.launchcode.taskcrusher.models.data.KidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/status")
public class StatusControllerApi {

    @Autowired
    private ChoreRepository choreRepository;

    @Autowired
    private KidRepository kidRepository;

//    // Endpoint for a parent to approve a completed chore and set points/dollars
//    @PostMapping("/approve/{choreId}")
////    public String approveChore(@PathVariable int choreId, Authentication authentication) {
//    public String approveChore(@PathVariable int choreId) {
//        Optional<Chore> choreOptional = choreRepository.findById(choreId);
//
//        if (!choreOptional.isPresent()) {
//            return "Chore not found";
//        }
//
//        Chore chore = choreOptional.get();
//
//        // Check if the authenticated user is the parent of the kid associated with the chore
//       // UserDto authenticatedUser = (UserDto) authentication.getPrincipal();
//        Kid kid = chore.getKid();
////
////        if (kid == null || !authenticatedUser.getId().equals(kid.getParent().getId())) {
////            return "User is not authorized to approve this chore.";
////        }
//
//        // Logic to check if the chore can be approved
//        if (!ChoreStatus.COMPLETED.equals(chore.getStatus())) {
//            return "Chore cannot be approved. Invalid status.";
//        }
//
//        int currentValue = kid.getPoints();
//        int choreValue = chore.getValue();
//
//        // Using ChoreValueType enum directly
//        ChoreValueType valueType = chore.getValueType();
//
//        if (ChoreValueType.POINTS.equals(valueType)) {
//            kid.setPoints(currentValue + choreValue);
//        } else if (ChoreValueType.DOLLARS.equals(valueType)) {
//
//            kid.setDollars(currentValue + choreValue);
//        } else {
//            return "Invalid value type for chore. Should be 'POINTS' or 'DOLLARS'.";
//        }
//
//        kidRepository.save(kid);
//
//        // Update the chore status to APPROVED
//        chore.setStatus(ChoreStatus.APPROVED);
//        choreRepository.save(chore);
//
//        return "Chore approved and points/dollars set for kid";
//    }
//
//}


    // Endpoint for a parent to approve a completed chore and set points/dollars
    @PostMapping("/approve/{choreId}/{parentId}")
    public String approveChore(@PathVariable int choreId, @PathVariable Long parentId) {
        Optional<Chore> choreOptional = choreRepository.findById(choreId);

        if (!choreOptional.isPresent()) {
            return "Chore not found";
        }

        Chore chore = choreOptional.get();
        Kid kid = chore.getKid();

        // Check if the provided parentId matches the parent associated with the kid
        if (kid == null || !parentId.equals(kid.getParent().getId())) {
            return "User is not authorized to approve this chore.";
        }

        // Logic to check if the chore can be approved
        if (!ChoreStatus.COMPLETED.equals(chore.getStatus())) {
            return "Chore cannot be approved. Invalid status.";
        }

        int currentValue = kid.getPoints();
        int choreValue = chore.getValue();

        // Using ChoreValueType enum directly
        String valueType = chore.getValueType();

        if ("Points".equalsIgnoreCase(valueType)) {
            kid.setPoints(currentValue + choreValue);
        } else if ("Dollars".equalsIgnoreCase(valueType)) {
            kid.setDollars(currentValue + choreValue);
        } else {
            return "Invalid value type for chore. Should be 'Points' or 'Dollars'.";
        }

        kidRepository.save(kid);

        // Update the chore status to APPROVED
        chore.setStatus(ChoreStatus.APPROVED);
        choreRepository.save(chore);

        return "Chore approved and points/dollars set for kid";
    }
}