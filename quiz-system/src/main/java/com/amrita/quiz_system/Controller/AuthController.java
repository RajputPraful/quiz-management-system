package com.amrita.quiz_system.Controller;

import com.amrita.quiz_system.Model.*;
import com.amrita.quiz_system.Repository.*;
import com.amrita.quiz_system.dto.LoginRequest; // Ensure this package exists
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private TeacherRepository teacherRepo;

    @PostMapping("/login/student")
    public ResponseEntity<?> loginStudent(@RequestBody LoginRequest request) {
        Optional<Student> student = studentRepo.findByRollNo(request.getId());
        if (student.isPresent() && student.get().getPassword().equals(request.getPassword())) {
            return ResponseEntity.ok(student.get()); // Return student details
        }
        return ResponseEntity.status(401).body("Invalid Student Credentials");
    }

    @PostMapping("/login/teacher")
    public ResponseEntity<?> loginTeacher(@RequestBody LoginRequest request) {
        Optional<Teacher> teacher = teacherRepo.findByTchrID(request.getId());
        if (teacher.isPresent() && teacher.get().getPassword().equals(request.getPassword())) {
            return ResponseEntity.ok(teacher.get()); // Return teacher details
        }
        return ResponseEntity.status(401).body("Invalid Teacher Credentials");
    }
}