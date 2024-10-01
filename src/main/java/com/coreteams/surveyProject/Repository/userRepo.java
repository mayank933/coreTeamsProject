package com.coreteams.surveyProject.Repository;

import com.coreteams.surveyProject.Entities.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepo extends JpaRepository<userEntity, Long> {
    userEntity findByEmail(String email);
}
