package org.launchcode.taskcrusher.controllers;


import org.launchcode.taskcrusher.models.Holiday;
import org.launchcode.taskcrusher.models.data.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
//This is Holiday Controller.The react front end will call this if its a public holiday.
//Based on that it will check if the Holiday table has this date then it will not do any.If its not
//there then it will insert.
//This table we will use while doing 2X for reward.
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class HolidayController {
    @Autowired
    private HolidayRepository holidayRepository;
    @PostMapping("/holiday-insert")
    public String createHoliday(@RequestBody Holiday holiday) {
        Optional<Holiday> existingHoliday = holidayRepository.findByHolidayDate(holiday.getHolidayDate());
        if (existingHoliday.isEmpty()) {
           holidayRepository.save(holiday);
            return "Inserted";
        }
        else {
            return "Already exist";
        }
    }
}
