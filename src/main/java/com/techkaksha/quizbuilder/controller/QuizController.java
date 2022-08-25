package com.techkaksha.quizbuilder.controller;

import com.techkaksha.quizbuilder.service.QuizService;
import com.techkaksha.quizbuilder.service.URLShorteningService;
import com.techkaksha.quizbuilder.model.Quiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/quizzes", produces = "application/json")
public class QuizController {

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);
    private static final String TODOS_DELETED_LOG = "Quiz id:{}  deleted";

    private static final String NEW_QUIZ_LOG = "New Quiz created id:{} with short URL:{}";
    @Value("${redirection.url}")
    private String redirectionURL;

    private QuizService quizService;

    private URLShorteningService urlShorteningService;

    public  QuizController(QuizService quizService, URLShorteningService urlShorteningService){
        this.quizService = quizService;
        this.urlShorteningService = urlShorteningService;
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> getQuiz(JwtAuthenticationToken jwt,
                                              @RequestParam(required = false, name = "page", defaultValue = "0") int page,
    @RequestParam(required = false, name = "size", defaultValue = "10") int size
    ){
        HashMap<String, Object> response = new HashMap<>();
        long quizCount = quizService.count(jwt);
        List<Quiz> quizzes = quizService.getQuiz(jwt, page, size);
        response.put("count", quizCount);
        response.put("quizzes", quizzes);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Map <String, Object>> create(JwtAuthenticationToken jwt, @RequestBody Quiz quiz){
        Quiz newQuiz = quizService.saveQuiz(jwt, quiz);
        String shortURL = urlShorteningService.generateShortURL(redirectionURL+newQuiz.getQuizId());
        logger.info(NEW_QUIZ_LOG, quiz.getQuizId(), shortURL);
        Map<String, Object> response = new HashMap<>();
        response.put("quiz", newQuiz);
        response.put("shortURL", shortURL);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Quiz> delete(JwtAuthenticationToken jwt, @PathVariable String id){
        Quiz deletedQuiz = quizService.deleteQuiz(jwt, Long.parseLong(id));
        logger.info(TODOS_DELETED_LOG, id);
        return ResponseEntity.ok(deletedQuiz);
    }



}
