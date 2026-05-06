package com.amrita.quiz_system.Controller;

import com.amrita.quiz_system.Model.*;
import com.amrita.quiz_system.Repository.QuizRepository;
import com.amrita.quiz_system.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin("*") // Crucial for connecting your frontend later
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizService quizService;

    // Teacher behavior: "Teachers to create quizzes"
    @PostMapping("/create")
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizRepository.save(quiz);
    }

    // Student behavior: "Students to attempt quizzes"
    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        return quizRepository.findById(id).orElseThrow();
    }

    // Evaluation: "Automatic evaluation of scores"
    @PostMapping("/{id}/submit")
    public Result submitQuiz(@PathVariable Long id,
                             @RequestParam String studentName,
                             @RequestBody List<Integer> answers) {
        return quizService.evaluateQuiz(id, studentName, answers);
    }

    // Teacher behavior: "viewAllResult() which shows result of all students"
    @GetMapping("/{id}/results")
    public List<Result> getQuizResults(@PathVariable Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        return quiz.getResults();
    }
}