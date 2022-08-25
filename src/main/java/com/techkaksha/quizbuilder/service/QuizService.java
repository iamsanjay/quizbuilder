package com.techkaksha.quizbuilder.service;

import com.techkaksha.quizbuilder.exception.EntityNotFoundException;
import com.techkaksha.quizbuilder.model.Quiz;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

public interface QuizService {
    public Quiz saveQuiz(JwtAuthenticationToken jwt, Quiz quiz);
    public Quiz deleteQuiz(JwtAuthenticationToken jwt, Long Id) throws EntityNotFoundException;

    public List<Quiz> getQuiz(JwtAuthenticationToken jwt, int page, int size);

    public Quiz getQuizForVisitor(Long id);

    public String evaluateQuiz(Quiz quiz);

    public long count(JwtAuthenticationToken jwt);

}
