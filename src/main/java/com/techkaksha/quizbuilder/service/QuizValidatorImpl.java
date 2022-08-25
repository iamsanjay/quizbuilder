package com.techkaksha.quizbuilder.service;

import com.techkaksha.quizbuilder.exception.SizeConstraintException;
import com.techkaksha.quizbuilder.model.Options;
import com.techkaksha.quizbuilder.model.Question;
import com.techkaksha.quizbuilder.model.Quiz;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizValidatorImpl implements QuizValidator{

    @Value("#{new Integer('${quiz.question.max.size}')}")
    private Integer quizQuestionsMax;

    @Value("#{new Integer('${quiz.question.min.size}')}")
    private Integer quizQuestionsMix;
    @Value("#{new Integer('${quiz.question.option.min.size}')}")
    private Integer quizQuestionsOptionMin;
    @Value("#{new Integer('${quiz.question.option.max.size}')}")
    private Integer quizQuestionsOptionMax;
    @Override
    public void validate(Quiz quiz) {
        ensureLimits(quiz);
    }

    private void ensureLimits(Quiz quiz){
        List<Question> questions = quiz.getQuestions();
        if(questions == null || questions.size() < quizQuestionsMix)
            throw new SizeConstraintException("Quiz at least should have "+ quizQuestionsMix + " question");
        if(questions.size() > quizQuestionsMax)
            throw new SizeConstraintException("Quiz should not contain more than "+ quizQuestionsMax+ " questions");
        for(Question question: questions){
            List<Options> options = question.getOptions();
            if(options == null || options.size() < quizQuestionsOptionMin)
                throw new SizeConstraintException("Question at least should have "+ quizQuestionsOptionMin +" options");
            if(options.size() > quizQuestionsOptionMax)
                throw new SizeConstraintException("Question should not contain more than " +  quizQuestionsOptionMax+" options");
        }
    }

}
