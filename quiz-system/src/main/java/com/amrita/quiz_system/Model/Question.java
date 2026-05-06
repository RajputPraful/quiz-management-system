package com.amrita.quiz_system.Model;



import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;
    private String[] options;
    private int correctOption;


    public boolean checkAnswer(int selectedOption) {
        return this.correctOption == selectedOption;
    }
}