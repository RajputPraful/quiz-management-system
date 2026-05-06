package com.amrita.quiz_system.Repository;

import com.amrita.quiz_system.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Inherits methods to support behaviors like getName()
    Optional<Student> findByRollNo(String rollNo);
}