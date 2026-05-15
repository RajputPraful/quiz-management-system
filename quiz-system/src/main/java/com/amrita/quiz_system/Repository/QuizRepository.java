package com.amrita.quiz_system.Repository;

import com.amrita.quiz_system.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    // This handles storing quiz details
}