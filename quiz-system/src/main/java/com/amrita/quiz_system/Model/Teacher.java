package com.amrita.quiz_system.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


// Teacher.java
@Entity @Data
public class Teacher {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String password;
    private Long id;
    private String name;
    private String tchrID;
}