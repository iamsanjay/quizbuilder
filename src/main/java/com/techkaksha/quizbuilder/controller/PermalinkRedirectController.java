package com.techkaksha.quizbuilder.controller;

import com.techkaksha.quizbuilder.service.URLShorteningMD5Impl;
import com.techkaksha.quizbuilder.service.URLShorteningService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/qu")
public class PermalinkRedirectController {

    private URLShorteningService urlShorteningService;

    public PermalinkRedirectController(URLShorteningMD5Impl urlShorteningService){
        this.urlShorteningService = urlShorteningService;
    }


    @GetMapping("/{shortURL}")
    public RedirectView redirection(@PathVariable String shortURL){
        String longURL = urlShorteningService.redirectToLongURL(shortURL);
        return new RedirectView(longURL);
    }

}
