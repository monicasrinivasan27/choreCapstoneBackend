package org.launchcode.taskcrusher.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.launchcode.taskcrusher.models.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KidUserDto {

    private int id;
    private String name;
    private String username;
    private String token;
    private UserDto user;
}


