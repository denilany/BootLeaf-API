package com.denilany.letsplayapi.dto;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String email;
    private String role;

    public JwtResponse(String token, String email, String role) {
        this.token = token;
        this.email = email;
        this.role = role;
    }
    // Getters and Setters
}