package org.launchcode.taskcrusher.controllers;

import org.launchcode.taskcrusher.models.ApiResponseBody;
import org.launchcode.taskcrusher.service.ApiService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
//@RequestMapping("/api")
public class ApiController {
    private final ApiService apiService;

    @GetMapping("/holiday-data")
    public Boolean endpointCallApi(@RequestParam String dueDate) {
        String  yearParam  = dueDate.substring(0, 4);

        ResponseEntity<ApiResponseBody[]> response = ResponseEntity.ok(apiService.getAllHoliday(yearParam));
        ApiResponseBody[] responseBodyArray = response.getBody();

        assert responseBodyArray != null;
        List<ApiResponseBody> apiResponseBodyList = Arrays.asList(responseBodyArray);

        // Check if a specific date exists in the list
        return apiResponseBodyList.stream()
                .anyMatch(apiResponseBody -> dueDate.equals(apiResponseBody.getDate()));
    }
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

}