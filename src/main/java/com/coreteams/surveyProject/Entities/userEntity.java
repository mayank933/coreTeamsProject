package com.coreteams.surveyProject.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class userEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    @Column(unique = true)
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @JsonProperty("role")
    private Role role;

    @JsonProperty("isLoggedIn")
    private boolean isLoggedIn;

}

