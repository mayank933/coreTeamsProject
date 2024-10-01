package com.coreteams.surveyProject.Services;

import com.coreteams.surveyProject.Entities.userEntity;
import com.coreteams.surveyProject.Repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class userService {

    @Autowired
    private userRepo userRepository;

    public ResponseEntity<?> createUser(userEntity userData){
        try {
            userRepository.save(userData);
            return new ResponseEntity<>("User created successfully!", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Login logic
    public ResponseEntity<?> loginUser(String email, String password) {
        userEntity user = userRepository.findByEmail(email);

        if (user == null) {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }
        // Basic password check (assuming password is stored in plain text)
        if (user.getPassword().equals(password)) {
            user.setLoggedIn(true);
            userRepository.save(user);
            return new ResponseEntity<>("User logged in successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    // Logout logic (for simplicity, you could just acknowledge the logout)
    public ResponseEntity<?> logoutUser(String email) {

        userEntity user = userRepository.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }
        user.setLoggedIn(false);
        userRepository.save(user);
        return new ResponseEntity<>("User logged out successfully!", HttpStatus.OK);
    }

}
