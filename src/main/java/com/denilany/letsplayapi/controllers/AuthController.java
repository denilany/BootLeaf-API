package com.denilany.letsplayapi.controllers;

import com.yourname.letsplay.dto.LoginRequest;
import com.yourname.letsplay.dto.JwtResponse;
import com.yourname.letsplay.models.User;
import com.yourname.letsplay.repositories.UserRepository;
import com.yourname.letsplay.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        // 1. Authenticate the user using Spring Security's Manager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 2. Generate the JWT Token
        String jwt = jwtUtils.generateTokenFromUsername(loginRequest.getEmail());
        
        // 3. Get User Details to include in response
        User user = userRepository.findByEmail(loginRequest.getEmail()).get();

        return ResponseEntity.ok(new JwtResponse(jwt, user.getEmail(), user.getRole()));
    }
}
