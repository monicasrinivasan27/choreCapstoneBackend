package org.launchcode.taskcrusher.controllers;

import org.launchcode.taskcrusher.models.Reward;
import org.launchcode.taskcrusher.models.data.RewardRepository;
import org.launchcode.taskcrusher.models.data.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rewards")
@CrossOrigin("http://localhost:3000")
public class RewardController {
    @Autowired
    private RewardRepository rewardRepository;

    @GetMapping("/list")
    public ResponseEntity<List<Reward>> displayAllRewards() {
        return new ResponseEntity<>(rewardRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Reward> createReward(@RequestBody Reward reward) {
        Reward createdReward = rewardRepository.save(reward);
        return new ResponseEntity<>(createdReward, HttpStatus.CREATED);
    }

    @GetMapping("/{rewardId}")
    public ResponseEntity<Reward> getRewardById(@PathVariable int rewardId) {
        Optional<Reward> optionalReward = rewardRepository.findById(rewardId);

        if (optionalReward.isPresent()) {
            return new ResponseEntity<>(optionalReward.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("edit/{rewardId}")
    public ResponseEntity<Reward> updateReward(@PathVariable int rewardId, @RequestBody Reward updatedReward) {
        Reward existingReward = rewardRepository.findById(rewardId).orElse(null);

        if (existingReward != null) {
            existingReward.setName(updatedReward.getName());
            existingReward.setDescription(updatedReward.getDescription());
            existingReward.setPoints(updatedReward.getPoints());

            Reward savedReward = rewardRepository.save(existingReward);
            return new ResponseEntity<>(savedReward, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("{rewardId}")
    public ResponseEntity<Void> DeleteReward(@PathVariable int rewardId)
    {
        rewardRepository.deleteById(rewardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}