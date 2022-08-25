package com.techkaksha.quizbuilder.util;

import com.techkaksha.quizbuilder.model.Options;
import com.techkaksha.quizbuilder.model.Question;


import java.util.ArrayList;
import java.util.List;

public class QuizUtility {
    public static Question getQuestion(String type, int numberOfOptions, int[] correct){
        List<Options> options = new ArrayList<>();
        Question question = new Question();
        question.setTitle("test_question");
        question.setType(type);
        for(int i=0; i < numberOfOptions; i++){
            Options option = new Options();
            option.setTitle("test_option");
            option.setOptions_id((long)i);
            option.setCorrect(false);
            for(int c: correct){
                if(i == c){
                    option.setCorrect(true);
                }
            }
            options.add(option);
        }
        question.setOptions(options);
        return question;
    }

}
