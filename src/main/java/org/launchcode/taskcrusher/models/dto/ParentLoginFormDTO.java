//package org.launchcode.taskcrusher.models.dto;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//
//public class ParentLoginFormDTO {
//
//    @NotNull(message = "Username is required")
//    @NotBlank(message = "Username is required")
//    @Size(min = 5, max = 30, message = "Username must be 5-50 characters long")
//    private String username;
//
//    @NotNull(message = "Password is required")
//    @NotBlank(message = "Password is required")
//    @Size(min = 8, message = "Password must be at least 8 characters long.")
//    private String password;
//
//    // ADDED 1/23 --------------------------------------------
//
//    @NotNull(message = "Email is required")
//    @NotBlank(message = "Email is required")
//    @Email(message = "Must use a valid email address.")
//    private String email;
//
//    // ADDED 1/23 --------------------------------------------
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//}
