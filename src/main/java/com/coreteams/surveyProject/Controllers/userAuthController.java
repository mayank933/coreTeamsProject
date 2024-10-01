package com.coreteams.surveyProject.Controllers;

import com.coreteams.surveyProject.DTOs.loginRequest;
import com.coreteams.surveyProject.Entities.userEntity;
import com.coreteams.surveyProject.Repository.userRepo;
import com.coreteams.surveyProject.Services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class userAuthController {

    @Autowired
    userService userServiceLayer;

    @Autowired
    userRepo userRepository;

    @GetMapping("/getAllUsers")
    public List<userEntity> getAllUsers () {
        return userRepository.findAll();
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUpUser(@RequestBody userEntity userData){
        return userServiceLayer.createUser(userData);
    }

    // Login endpoint
    @PutMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody loginRequest data) {
        return userServiceLayer.loginUser(data.getEmail(), data.getPassword());
    }

    // Logout endpoint
    @PutMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody loginRequest data) {
        return userServiceLayer.logoutUser(data.getEmail());
    }

}
