package com.coreteams.surveyProject.Controllers;

import com.coreteams.surveyProject.Entities.Role;
import com.coreteams.surveyProject.Entities.responseEntity;
import com.coreteams.surveyProject.Entities.surveyEntity;
import com.coreteams.surveyProject.Entities.userEntity;
import com.coreteams.surveyProject.Repository.responseRepo;
import com.coreteams.surveyProject.Repository.surveyRepo;
import com.coreteams.surveyProject.Repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/respondents")
public class responseController {

    @Autowired
    private surveyRepo surveyRepository;

    @Autowired
    private userRepo userRepository;

    @Autowired
    private responseRepo responseRepository;

    // Get Eligible Surveys
    @GetMapping("/eligible-surveys")
    public ResponseEntity<?> getEligibleSurveys(@RequestParam String email) {
        userEntity respondent = userRepository.findByEmail(email);
        if(respondent == null || respondent.getRole() != Role.RESPONDENT) {
            return ResponseEntity.status(403).body("Access denied.");
        }

        List<surveyEntity> surveys = surveyRepository.findAll().stream().filter(survey -> {
            boolean genderMatch = (survey.getTargetGender() == null || survey.getTargetGender().equalsIgnoreCase(respondent.getGender()));
            boolean ageMatch = respondent.getAge() >= survey.getTargetAgeMin() && respondent.getAge() <= survey.getTargetAgeMax();
            boolean timeMatch = LocalDateTime.now().isAfter(survey.getStartTime()) && LocalDateTime.now().isBefore(survey.getEndTime());
            return genderMatch && ageMatch && timeMatch;
        }).toList();

        return ResponseEntity.ok(surveys);
    }

    // Submit Survey Response
    @PostMapping("/submit/{surveyId}")
    public ResponseEntity<?> submitSurvey(@PathVariable Long surveyId, @RequestParam String email, @RequestBody String answers) {
        userEntity respondent = userRepository.findByEmail(email);
        if(respondent == null || respondent.getRole() != Role.RESPONDENT) {
            return ResponseEntity.status(403).body("Access denied.");
        }

        Optional<surveyEntity> surveyOpt = surveyRepository.findById(surveyId);
        if(!surveyOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Survey not found.");
        }

        surveyEntity survey = surveyOpt.get();

        // Check if already submitted
        Optional<responseEntity> existingResponse = responseRepository.findBySurveyAndRespondent(survey, respondent);
        if(existingResponse.isPresent()) {
            return ResponseEntity.badRequest().body("You have already submitted this survey.");
        }

        // Check eligibility
        boolean genderMatch = (survey.getTargetGender() == null || survey.getTargetGender().equalsIgnoreCase(respondent.getGender()));
        boolean ageMatch = respondent.getAge() >= survey.getTargetAgeMin() && respondent.getAge() <= survey.getTargetAgeMax();
        boolean timeMatch = LocalDateTime.now().isAfter(survey.getStartTime()) && LocalDateTime.now().isBefore(survey.getEndTime());
        if(!(genderMatch && ageMatch && timeMatch)) {
            return ResponseEntity.badRequest().body("You are not eligible to take this survey.");
        }

        // Save response
        responseEntity response = new responseEntity();
        response.setSurvey(survey);
        response.setRespondent(respondent);
        response.setAnswers(answers); // Assuming answers are sent as a JSON string
        response.setSubmittedAt(LocalDateTime.now());

        responseRepository.save(response);

        return ResponseEntity.ok("Survey submitted successfully.");
    }
}
