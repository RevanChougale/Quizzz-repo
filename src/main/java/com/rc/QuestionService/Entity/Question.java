package com.rc.QuestionService.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//here we removed annotaion entity and genrated type bcz we dont save this class in to db thats why we removed
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private  Long questionId;
    private String question;
    private  long quizId;

}
