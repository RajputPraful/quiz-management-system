package com.amrita.quiz_system.Repository;

import com.amrita.quiz_system.Model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    // This displays the marks of the student
}