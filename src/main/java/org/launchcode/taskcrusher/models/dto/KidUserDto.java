package org.launchcode.taskcrusher.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KidUserDto {

    private int id;
    private String firstName;
    private String username;
    private String token;

}
