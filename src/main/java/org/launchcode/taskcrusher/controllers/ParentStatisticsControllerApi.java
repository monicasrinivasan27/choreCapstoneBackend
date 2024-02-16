package org.launchcode.taskcrusher.controllers;


import org.launchcode.taskcrusher.enums.ChoreStatus;
import org.launchcode.taskcrusher.models.Chore;
import org.launchcode.taskcrusher.models.Kid;
import org.launchcode.taskcrusher.models.data.ChoreRepository;
import org.launchcode.taskcrusher.models.data.KidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/parent-dashboard")
public class ParentStatisticsControllerApi {

    @Autowired
    private ChoreRepository choreRepository;

    @Autowired
    private KidRepository kidRepository;

    @GetMapping("/statistics")
    public List<Map<String, Object>> getParentDashboardStatistics(@RequestParam Long id) {
        // Get all kids from the database
        List<Kid> kids = kidRepository.findByParentId(id);
        // Create a list to store information about each child
        List<Map<String, Object>> kidsCards = new ArrayList<>();

        // Loop through each child
        for (Kid kid : kids) {
            // Create a card with basic information about the child
            Map<String, Object> kidCard = createKidCard(kid);
            // Add the child's card to the list
            kidsCards.add(kidCard);
        }

        // Return the list of child cards
        return kidsCards;
    }

    private Map<String, Object> createKidCard(Kid kid) {
        // Create a card to store information about the child
        Map<String, Object> kidCard = new HashMap<>();
        // Add basic information about the child to the card
        kidCard.put("kidId", kid.getKidId());
        kidCard.put("kidName", kid.getName());
        kidCard.put("totalPoints", kid.getPoints());
        kidCard.put("totalDollars", kid.getDollars());

        // Calculate and add information about the number of chores assigned to the child
        List<Chore> assignedChores = choreRepository.findByKid(kid);
        int totalAssignedChores = assignedChores.size();
        // Add the total assigned chores to the card
        kidCard.put("totalAssignedChores", totalAssignedChores);

        // Calculate and add information about the number of approved chores for the child
        int totalApprovedChores = 0;
        // Loop through each assigned chore and count the approved ones
        for (Chore chore : assignedChores) {
            if (ChoreStatus.APPROVED.equals(chore.getStatus())) {
                totalApprovedChores++;
            }
        }
        // Add the total approved chores to the card
        kidCard.put("totalApprovedChores", totalApprovedChores);

        // Return the complete child card
        return kidCard;
    }

}


