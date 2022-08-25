package com.techkaksha.quizbuilder.service;

public interface URLShorteningService {
    String generateShortURL(String longURL);
    String redirectToLongURL(String shortURL);
}
