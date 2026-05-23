package com.rc.QuestionService.serviceimpl;

import com.rc.QuestionService.Entity.Quiz;

import com.rc.QuestionService.repository.QuizRepo;
import com.rc.QuestionService.service.QuestionClient;
import com.rc.QuestionService.service.QuizService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private QuizRepo quizRepo;
    private QuestionClient questionClient;


    // Constructor injection
    public QuizServiceImpl(QuizRepo quizRepo, QuestionClient questionClient) {
        this.quizRepo = quizRepo;
        this.questionClient = questionClient;

    }

    // -----------------------------
    // ✅ 1. CREATE (No resilience needed)
    // -----------------------------
    @Override
    public Quiz add(Quiz quiz) {

        return quizRepo.save(quiz);



    }

    // -----------------------------
    // ✅ 2. GET ALL QUIZ (CALLER METHOD)
    // -----------------------------
    @CircuitBreaker(name = "quizService", fallbackMethod = "fallbackGet")
    @Retry(name = "quizService")
    @Override
    public List<Quiz> get() {

        // Step 1: Get all quizzes from DB
        return quizRepo.findAll().stream().map(quiz -> {

            // Step 2: Call Question Service (Feign call)
            // This may FAIL → handled by Circuit Breaker
            quiz.setQuestions(
                    questionClient.questionsOfQuiz(quiz.getId())
            );

            return quiz;

        }).toList();
    }

    // -----------------------------
    // ✅ 3. FALLBACK for get()
    // -----------------------------
    public List<Quiz> fallbackGet(Throwable ex) {

        // This runs when Question Service is DOWN
        System.out.println("Fallback triggered for get(): " + ex.getMessage());

        // Return safe/default data
        return List.of(new Quiz());
    }


    // ✅ 4. GET QUIZ BY ID (CALLER METHOD)

    @CircuitBreaker(name = "quizService", fallbackMethod = "fallbackGetById")
    @Retry(name = "quizService")
    @Override
    public Quiz get(Long id) {

       System.out.println("FROM DATABASE");

        // Step 1: Get quiz from DB
        Quiz quiz = quizRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        // Step 2: Call Question Service (may fail)
        quiz.setQuestions(
                questionClient.questionsOfQuiz(quiz.getId())
        );

        return quiz;
    }

    // -----------------------------
    // ✅ 5. FALLBACK for get(id)
    // -----------------------------
    public Quiz fallbackGetById(Long id, Throwable ex) {

        System.out.println("Fallback triggered for get(id): " + ex.getMessage());

        // Return safe/default object
        Quiz quiz = new Quiz();
        quiz.setId(id);
        quiz.setTitle("Fallback Quiz (Service Down)");

        return quiz;
    }
}