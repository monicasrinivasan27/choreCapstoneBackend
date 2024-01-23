package org.launchcode.taskcrusher.controllers;

import org.launchcode.taskcrusher.service.ApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final ApiService apiService;

     @GetMapping("/holiday-data")
    public ResponseEntity<?> endpointCallApi() {
        return ResponseEntity.ok(apiService.getAllHoliday());
    }

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }



}
