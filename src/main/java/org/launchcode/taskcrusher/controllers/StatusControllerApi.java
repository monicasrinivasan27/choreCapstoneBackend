package org.launchcode.taskcrusher.controllers;


import org.launchcode.taskcrusher.enums.ChoreStatus;
import org.launchcode.taskcrusher.models.Chore;
import org.launchcode.taskcrusher.models.Kid;
import org.launchcode.taskcrusher.models.data.ChoreRepository;
import org.launchcode.taskcrusher.models.data.KidRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String approveChore(@PathVariable int choreId) {
        Optional<Chore> choreOptional = choreRepository.findById(choreId);

        if (choreOptional.isPresent()) {
            Chore chore = choreOptional.get();

            //  logic to check if the chore can be approved
            if (ChoreStatus.COMPLETED.equals(chore.getStatus())) {

                Kid kid = chore.getKid();
                if (kid != null) {
                    int currentValue = kid.getPoints();
                    int choreValue = chore.getValue();
                    String valueType = chore.getValueType();

                    if ("points".equalsIgnoreCase(valueType)) {
                        kid.setPoints(currentValue + choreValue);
                    } else if ("dollars".equalsIgnoreCase(valueType)) {
                        kid.setDollars(currentValue + choreValue);
                    } else {
                        return "Invalid value type for chore. Should be 'points' or 'dollars'.";
                    }

                    kidRepository.save(kid);
                }

                // Update the chore status to APPROVED
                chore.setStatus(ChoreStatus.APPROVED);
                choreRepository.save(chore);

                return "Chore approved and points/dollars set for kid";
            } else {
                return "Chore cannot be approved. Invalid status.";
            }
        } else {
            return "Chore not found";
        }
    }

}
