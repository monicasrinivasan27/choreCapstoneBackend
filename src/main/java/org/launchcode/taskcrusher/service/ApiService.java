package org.launchcode.taskcrusher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.logging.ErrorManager;

@Service
public class ApiService {
    private static final String url = "https://date.nager.at/api/v3/publicholidays";
    private static final String countryCode = "US";
    private static final String yearCode = "2024";
    private static final String urlFull=url+"/"+yearCode+"/"+countryCode;

    @Autowired
    private RestTemplate restTemplate;

    public Object getAllHoliday() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(urlFull, String.class);
            return response.getBody();
        } catch (Exception errors) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while calling the API", errors);
        }

    }


}
