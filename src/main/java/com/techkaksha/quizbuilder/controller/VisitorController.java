package com.techkaksha.quizbuilder.controller;

import com.techkaksha.quizbuilder.model.Quiz;
import com.techkaksha.quizbuilder.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/v1/visitor/quiz")
public class VisitorController {

    QuizService quizService;

    public VisitorController(QuizService quizService){ this.quizService = quizService; }

    @GetMapping("/{id}")
    public Quiz quiz(@PathVariable String id){
        System.out.println("ENTERED INTO QUIZ");
        return quizService.getQuizForVisitor(Long.parseLong(id));
    }

    @PostMapping("/evaluate")
    public ResponseEntity<Map<String, String>> evaluate(@RequestBody Quiz quiz){
        System.out.println(quiz);
        String resultString = quizService.evaluateQuiz(quiz);
        HashMap<String, String> result = new HashMap<>();
        result.put("result", resultString);
        return ResponseEntity.ok(result);
    }

}
