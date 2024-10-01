package com.coreteams.surveyProject.Entities;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "responses", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"survey_id", "respondent_id"})
})
public class responseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = false)
    private surveyEntity survey;

    @ManyToOne
    @JoinColumn(name = "respondent_id")
    private userEntity respondent;

    @Lob
    private String answers; // Consider using a JSON string or a separate table for detailed answers

    private LocalDateTime submittedAt;

}
