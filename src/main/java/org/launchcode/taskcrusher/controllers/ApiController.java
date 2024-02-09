package org.launchcode.taskcrusher.controllers;

import org.launchcode.taskcrusher.models.ApiResponseBody;
import org.launchcode.taskcrusher.service.ApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
//API controller
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {
    private final ApiService apiService;

    @GetMapping("/holiday-data")
    //Gets the Input date from the React front end when the Parent clicks on the Date.
    public Boolean endpointCallApi(@RequestParam String dueDate) {
        //Getting the Year alone
        String  yearParam  = dueDate.substring(0, 4);
        //The API needs the Year and hence passing this value to it.
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