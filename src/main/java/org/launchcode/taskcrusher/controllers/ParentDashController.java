package org.launchcode.taskcrusher.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ParentDashController {

    @GetMapping("/parentDash")
    public ResponseEntity<String> parentDash() {
        return ResponseEntity.ok("Yay! You did it!");
    }

}
