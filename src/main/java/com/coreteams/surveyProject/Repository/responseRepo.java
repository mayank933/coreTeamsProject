package com.coreteams.surveyProject.Repository;

import com.coreteams.surveyProject.Entities.responseEntity;
import com.coreteams.surveyProject.Entities.surveyEntity;
import com.coreteams.surveyProject.Entities.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface responseRepo extends JpaRepository<responseEntity, Long> {
    Optional<responseEntity> findBySurveyAndRespondent(surveyEntity survey, userEntity respondent);
}
