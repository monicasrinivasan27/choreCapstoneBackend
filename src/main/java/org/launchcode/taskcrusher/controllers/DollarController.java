package org.launchcode.taskcrusher.controllers;

import org.launchcode.taskcrusher.models.Kid;
import org.launchcode.taskcrusher.models.Reward;
import org.launchcode.taskcrusher.models.data.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.launchcode.taskcrusher.models.data.KidRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dollars")
@CrossOrigin("http://localhost:3000")
public class DollarController {
    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private KidRepository kidRepository;


    @PutMapping("/claim/{claimDollars}/{kidId}")
    public ResponseEntity ClaimReward(@PathVariable double claimDollars, @PathVariable int kidId) {
        Optional<Kid> kidOptional = kidRepository.findById(kidId);
        System.out.println("Claiming dollats");
        if (kidOptional.isPresent()) {
            Kid kid = kidOptional.get();
            double currentDollars = kid.getDollars();
            if (currentDollars < claimDollars) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Not enough dollars to claim");
            } else {
                kid.setDollars(currentDollars - claimDollars);
                kidRepository.save(kid);
            }
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(kid);
        }
        else{
            return new ResponseEntity<>("Kid is not present", HttpStatus.BAD_REQUEST);
        }
    }
}