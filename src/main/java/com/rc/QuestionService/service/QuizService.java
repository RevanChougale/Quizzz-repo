package com.rc.QuestionService.service;

import com.rc.QuestionService.Entity.Quiz;

import java.util.List;

public interface QuizService {

    Quiz add (Quiz quiz);

    List<Quiz> get();

    Quiz get(Long id);
}
