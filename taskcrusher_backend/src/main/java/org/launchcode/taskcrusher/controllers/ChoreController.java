package org.launchcode.taskcrusher.controllers;

import org.launchcode.taskcrusher.models.Chore;
import org.launchcode.taskcrusher.models.data.ChoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chores")
@CrossOrigin
public class ChoreController {

    @Autowired
    private ChoreRepository choreRepository;

    @GetMapping("/list")
    public List<Chore> getAllChores() {
        return choreRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Chore> createChore(@RequestBody Chore chore) {
        Chore createdChore = choreRepository.save(chore);
        return new ResponseEntity<>(createdChore, HttpStatus.CREATED);
    }

}