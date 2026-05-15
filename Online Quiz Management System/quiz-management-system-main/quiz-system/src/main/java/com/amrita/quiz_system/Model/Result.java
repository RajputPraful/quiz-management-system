package com.amrita.quiz_system.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Result {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentName;
    private int score;
    private int totalMarks;
}