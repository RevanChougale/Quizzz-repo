package com.rc.QuestionService.repository;

import com.rc.QuestionService.Entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepo extends JpaRepository <Quiz,Long> {
}
