package com.techkaksha.quizbuilder.service;

import com.techkaksha.quizbuilder.exception.EntityNotFoundException;
import com.techkaksha.quizbuilder.model.Options;
import com.techkaksha.quizbuilder.model.Question;
import com.techkaksha.quizbuilder.model.Quiz;
import com.techkaksha.quizbuilder.repo.QuizRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuizServiceImpl implements  QuizService{

    private QuizRepository quizRepository;

    private QuizValidator quizValidator;
    private static final String QUIZ_RESULT = "You answered %s/%s questions correctly.";

    public QuizServiceImpl(QuizRepository quizRepository, QuizValidator quizValidator){
        this.quizRepository = quizRepository;
        this.quizValidator = quizValidator;
    }


    @Override
    public Quiz saveQuiz(JwtAuthenticationToken jwt, Quiz quiz) {
        String username = extractUser(jwt);
        quizValidator.validate(quiz);
        quiz.setUserName(username);
        Quiz savedQuiz = quizRepository.save(quiz);
        return savedQuiz;
    }

    @Override
    public Quiz deleteQuiz(JwtAuthenticationToken jwt, Long id) throws EntityNotFoundException {
        String username = extractUser(jwt);
        Quiz quiz = quizRepository.findQuizByUserNameAndQuizId(username, id);
        if(quiz == null)
            throw new EntityNotFoundException(Quiz.class, "id", String.valueOf(id));
        quizRepository.deleteById(id);
        return quiz;
    }

    @Override
    public List<Quiz> getQuiz(JwtAuthenticationToken jwt, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        String username = extractUser(jwt);
        return quizRepository.findQuizByUserName(username, pageable);
    }


    @Override
    public Quiz getQuizForVisitor(Long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if(quiz.isEmpty()){
            return null;
        }
        removeAnswers(quiz.get());
        return quiz.get();
    }

    @Override
    public String evaluateQuiz(Quiz quiz) {
        Optional<Quiz> correctQuiz = quizRepository.findById(quiz.getQuizId());
        if(correctQuiz.isEmpty())
            throw new EntityNotFoundException(Quiz.class,"Id", String.valueOf(quiz.getQuizId()));
        return String.format(QUIZ_RESULT, checkAnswer(quiz, correctQuiz.get()),correctQuiz.get().getQuestions().size());
    }

    @Override
    public long count(JwtAuthenticationToken jwt) {
        String username = extractUser(jwt);
        return quizRepository.countAllByUserName(username);
    }

    private void removeAnswers(Quiz quiz){
        quiz
                .getQuestions()
                .stream()
                .forEach(question -> question
                        .getOptions()
                        .stream()
                        .forEach(option -> option.setCorrect(false)));
    }

    private int checkAnswer(Quiz userQuiz, Quiz correctQuiz){
        AtomicInteger correctAnswer = new AtomicInteger();
        userQuiz
                .getQuestions()
                .stream()
                .forEach(question -> {
                    Optional<Question> question1 = correctQuiz
                            .getQuestions()
                            .stream()
                            .filter(correctQuestion -> correctQuestion.getQuestionId().equals(question.getQuestionId()))
                            .findFirst();
                    if(question1.isPresent()){
                        boolean correct = true;
                        for(Options option: question.getOptions()){
                            if(question1.get()
                                    .getOptions()
                                    .stream()
                                    .filter(option1 -> option1.getOptions_id().equals(option.getOptions_id()) && option1.isCorrect() == option.isCorrect())
                                    .findFirst().isEmpty()) {
                                correct = false;
                            }
                        }
                        if(correct)
                            correctAnswer.set(correctAnswer.get() + 1);
                    }

                });
        return correctAnswer.get();
    }
    private String extractUser(JwtAuthenticationToken jwt){
        return (String)jwt.getTokenAttributes().get("email");
    }


}
