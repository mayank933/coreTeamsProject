package com.coreteams.surveyProject.DTOs;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class surveyDTO {

    private String title;
    private String description;
    private String targetGender;
    private Integer targetAgeMin;
    private Integer targetAgeMax;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
