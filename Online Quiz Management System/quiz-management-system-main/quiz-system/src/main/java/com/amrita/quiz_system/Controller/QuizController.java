package com.amrita.quiz_system.Controller;

import com.amrita.quiz_system.Model.*;
import com.amrita.quiz_system.Repository.QuizRepository;
import com.amrita.quiz_system.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin("*")
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizService quizService;

    // 1. GET ALL QUIZZES (Added this to fix the 400/Whitelabel error)
    @GetMapping("/list")
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    // 2. Teacher behavior: "Teachers to create quizzes"
    @PostMapping("/create")
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizRepository.save(quiz);
    }

    // 3. Student behavior: "Get specific quiz details"
    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        return quizRepository.findById(id).orElseThrow();
    }

    // 4. Evaluation: "Automatic evaluation of scores"
    @PostMapping("/{id}/submit")
    public Result submitQuiz(@PathVariable Long id,
                             @RequestParam String studentName,
                             @RequestBody List<Integer> answers) {
        return quizService.evaluateQuiz(id, studentName, answers);
    }

    // 5. Teacher behavior: "viewAllResult()"
    @GetMapping("/{id}/results")
    public List<Result> getQuizResults(@PathVariable Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        return quiz.getResults();
    }
}