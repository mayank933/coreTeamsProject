package com.coreteams.surveyProject.Entities;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.catalina.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "surveys")
public class surveyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<responseEntity> responses = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "created_by")
    private userEntity createdBy;

    private String targetGender;
    private Integer targetAgeMin;
    private Integer targetAgeMax;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
