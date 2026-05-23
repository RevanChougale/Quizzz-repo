package com.rc.QuestionService.client;

import lombok.Data;

@Data
public class QuestionDTO {
    private  Long questionId;
    private String question;
    private  long quizId;
}
