package com.techkaksha.quizbuilder.service;

import com.techkaksha.quizbuilder.model.Question;
import com.techkaksha.quizbuilder.repo.QuizRepository;
import com.techkaksha.quizbuilder.util.QuizUtility;
import com.techkaksha.quizbuilder.model.Quiz;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class QuizServiceImplTest {
    private QuizService quizService;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuizValidator quizValidator;

    public QuizServiceImplTest(){
        quizService = new QuizServiceImpl(quizRepository, quizValidator);
    }



    @Test
    public void test_evaluateQuiz_single() {
        Question correctQuestion = QuizUtility.getQuestion("radio", 5,new int[]{0});
        List<Question> correctQuestions = Arrays.asList(correctQuestion);
        Quiz correctQuiz = new Quiz();
        correctQuiz.setQuizId(10l);
        correctQuiz.setQuestions(correctQuestions);


        Question wrongQuestion = QuizUtility.getQuestion("radio", 5,new int[]{1});
        List<Question> wrongQuestions = Arrays.asList(correctQuestion);
        Quiz wrongQuiz = new Quiz();
        wrongQuiz.setQuizId(10l);
        wrongQuiz.setQuestions(correctQuestions);

        System.out.println(quizRepository);
        Mockito.when(quizRepository.findById(wrongQuiz.getQuizId())).
                thenReturn(Optional.empty());
        System.out.println(quizService.evaluateQuiz(wrongQuiz));

        Assertions.assertEquals("You answered 1/1 questions correctly.", quizService.evaluateQuiz(wrongQuiz));

    }

}
