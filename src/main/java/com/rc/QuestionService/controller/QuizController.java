package com.rc.QuestionService.controller;

import com.rc.QuestionService.Entity.Quiz;
import com.rc.QuestionService.repository.QuizRepo;
import com.rc.QuestionService.serviceimpl.QuizServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;




@RestController
@RequestMapping("/quiz")
public class QuizController {

    private QuizServiceImpl quizService;

    public QuizController(QuizServiceImpl quizService) {
        this.quizService = quizService;
    }

    // ✅ CREATE quiz (ONLY ADMIN allowed)
    @PostMapping
    public Quiz add(@RequestBody Quiz quiz,
                    @RequestHeader("X-Role") String role) {

        // 🔒 Allow only ADMIN
        System.out.println("ROLE RECEIVED FROM HEADER: " + role);
        if (role == null || !role.equals("ROLE_ADMIN")) {

           // throw new RuntimeException("Access Denied - Only ADMIN allowed");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only ADMIN allowed");
        }

        return quizService.add(quiz);
    }

    // ✅ GET all quizzes (ADMIN + USER allowed)
    @GetMapping
    public List<Quiz> get() {
        return quizService.get();
    }



    // ✅ GET one quiz (ADMIN + USER allowed)
    @GetMapping("/{id}")
    public Quiz getOne(@PathVariable Long id) {
        return quizService.get(id);
    }
}
