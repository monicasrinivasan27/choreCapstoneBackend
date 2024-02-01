package org.launchcode.taskcrusher.service;

import org.launchcode.taskcrusher.models.ApiResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
public class ApiService {
    private static final String url = "https://date.nager.at/api/v3/publicholidays";
    private static final String countryCode = "US";

    @Autowired
    private RestTemplate restTemplate;

    public ApiResponseBody[] getAllHoliday( String year) {

        String urlFull = url + "/" + year + "/" + countryCode;
        try {
            ResponseEntity<ApiResponseBody[]> response = restTemplate.getForEntity(urlFull, ApiResponseBody[].class);
            return response.getBody();
        } catch (Exception errors) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while calling the API", errors);
        }

    }
}