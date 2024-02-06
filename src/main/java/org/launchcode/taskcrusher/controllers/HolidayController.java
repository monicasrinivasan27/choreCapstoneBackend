package org.launchcode.taskcrusher.controllers;


import org.launchcode.taskcrusher.models.Holiday;
import org.launchcode.taskcrusher.models.data.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
//@RequestMapping("/holiday-insert")
@CrossOrigin(origins = "http://localhost:3000")
public class HolidayController {
    @Autowired
    private HolidayRepository holidayRepository;
    @PostMapping("/holiday-insert")
    public String createHoliday(@RequestBody Holiday holiday) {
        Optional<Holiday> existingHoliday = holidayRepository.findByHolidayDate(holiday.getHolidayDate());
     //   System.out.println("Received Holiday object:");
     //   System.out.println("Name: " + holiday.getHolidayDate());
        if (existingHoliday.isEmpty()) {
           holidayRepository.save(holiday);
            return "Inserted";
        }
        else {
            return "Already exist";
        }
    }
}
