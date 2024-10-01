package com.coreteams.surveyProject.Controllers;

import com.coreteams.surveyProject.DTOs.surveyDTO;
import com.coreteams.surveyProject.Entities.Role;
import com.coreteams.surveyProject.Entities.surveyEntity;
import com.coreteams.surveyProject.Entities.userEntity;
import com.coreteams.surveyProject.Repository.surveyRepo;
import com.coreteams.surveyProject.Repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/surveys")
public class surveyController {

    @Autowired
    private surveyRepo surveyRepository;

    @Autowired
    private userRepo userRepository;

    @GetMapping("/getAllSurveys")
    public List<surveyEntity> getAllSurveys(){
        return surveyRepository.findAll();
    }

    // Create Survey
    @PostMapping("/create")
    public ResponseEntity<?> createSurvey(@RequestParam String email, @RequestBody surveyDTO surveyDTO) {
        userEntity coordinator = userRepository.findByEmail(email);
        if(coordinator == null || coordinator.getRole() != Role.COORDINATOR) {
            return ResponseEntity.status(403).body("Access denied.");
        }

        surveyEntity survey = new surveyEntity();
        survey.setTitle(surveyDTO.getTitle());
        survey.setDescription(surveyDTO.getDescription());
        survey.setCreatedBy(coordinator);
        survey.setTargetGender(surveyDTO.getTargetGender());
        survey.setTargetAgeMin(surveyDTO.getTargetAgeMin());
        survey.setTargetAgeMax(surveyDTO.getTargetAgeMax());
        survey.setStartTime(surveyDTO.getStartTime());
        survey.setEndTime(surveyDTO.getEndTime());

        surveyRepository.save(survey);

        return ResponseEntity.ok("Survey created successfully.");
    }

    // Edit Survey
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editSurvey(@PathVariable Long id, @RequestParam String email, @RequestBody surveyDTO surveyDTO) {
        userEntity coordinator = userRepository.findByEmail(email);
        if(coordinator == null || coordinator.getRole() != Role.COORDINATOR) {
            return ResponseEntity.status(403).body("Access denied.");
        }

        Optional<surveyEntity> surveyOpt = surveyRepository.findById(id);
        if(!surveyOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Survey not found.");
        }

        surveyEntity survey = surveyOpt.get();
        if(!survey.getCreatedBy().getId().equals(coordinator.getId())) {
            return ResponseEntity.status(403).body("You can only edit your own surveys.");
        }

        survey.setTitle(surveyDTO.getTitle());
        survey.setDescription(surveyDTO.getDescription());
        survey.setTargetGender(surveyDTO.getTargetGender());
        survey.setTargetAgeMin(surveyDTO.getTargetAgeMin());
        survey.setTargetAgeMax(surveyDTO.getTargetAgeMax());
        survey.setStartTime(surveyDTO.getStartTime());
        survey.setEndTime(surveyDTO.getEndTime());

        surveyRepository.save(survey);

        return ResponseEntity.ok("Survey updated successfully.");
    }

    // Review Submissions (Tabulate Results)
    @GetMapping("/results/{id}")
    public ResponseEntity<?> getSurveyResults(@PathVariable Long id, @RequestParam String email) {
        userEntity coordinator = userRepository.findByEmail(email);
        if(coordinator == null || coordinator.getRole() != Role.COORDINATOR) {
            return ResponseEntity.status(403).body("Access denied.");
        }

        Optional<surveyEntity> surveyOpt = surveyRepository.findById(id);
        if(!surveyOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Survey not found.");
        }

        surveyEntity survey = surveyOpt.get();
        if(!survey.getCreatedBy().getId().equals(coordinator.getId())) {
            return ResponseEntity.status(403).body("You can only view results of your own surveys.");
        }

        // Fetch responses and tabulate
        // For simplicity, return the number of responses
        long responseCount = survey.getResponses().size();

        return ResponseEntity.ok("Total Responses: " + responseCount);
    }

}
