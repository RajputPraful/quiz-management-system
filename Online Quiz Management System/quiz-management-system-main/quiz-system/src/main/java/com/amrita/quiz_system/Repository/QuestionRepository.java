package com.amrita.quiz_system.Repository;

import com.amrita.quiz_system.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    // This handles storing question information
}