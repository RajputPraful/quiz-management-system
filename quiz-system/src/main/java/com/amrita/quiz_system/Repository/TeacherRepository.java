package com.amrita.quiz_system.Repository;

import com.amrita.quiz_system.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // Supports the teacher's responsibility to manage quiz content [cite: 83]
    Optional<Teacher> findByTchrID(String tchrID);
}