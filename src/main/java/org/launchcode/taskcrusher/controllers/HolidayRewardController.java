package org.launchcode.taskcrusher.controllers;


import org.launchcode.taskcrusher.models.Holiday;
import org.launchcode.taskcrusher.models.data.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class HolidayRewardController {
    @Autowired
    private HolidayRepository holidayRepository;
    @GetMapping("/holiday-reward")
    //The points will be doubled if it's a Holiday else the point will be same.
    //Passing two request parameter Date and Point.
    public int getRewardValue(
            @RequestParam LocalDate dueDate,
            @RequestParam int points ) {
        int rewardPoints=0;

        Optional<Holiday> existingHoliday = holidayRepository.findByHolidayDate(dueDate);
     if (existingHoliday.isEmpty()) {
            rewardPoints=points;
        }
        else {
            rewardPoints=(2*points);
        }
        return rewardPoints;


    }

}

