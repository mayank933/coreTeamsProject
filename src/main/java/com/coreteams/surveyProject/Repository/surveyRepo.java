package com.coreteams.surveyProject.Repository;

import com.coreteams.surveyProject.Entities.surveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface surveyRepo extends JpaRepository<surveyEntity, Long> {
    List<surveyEntity> findByTargetGenderAndTargetAgeMinLessThanEqualAndTargetAgeMaxGreaterThanEqual(
            String gender, Integer ageMin, Integer ageMax);

}
