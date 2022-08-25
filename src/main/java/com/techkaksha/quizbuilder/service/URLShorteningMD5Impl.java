package com.techkaksha.quizbuilder.service;

import com.techkaksha.quizbuilder.exception.URLNotFoundException;
import com.techkaksha.quizbuilder.model.Permalink;
import com.techkaksha.quizbuilder.repo.PermalinkRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Optional;

@Service
public class URLShorteningMD5Impl implements URLShorteningService{

    private static final int SHORT_URL_LENGTH = 6;

    private PermalinkRepository permalinkRepository;


    public URLShorteningMD5Impl(PermalinkRepository permalinkRepository){
        this.permalinkRepository = permalinkRepository;
    }

    @Override
    public String generateShortURL(String longURL){
        System.out.println(longURL);
        Permalink existingPermalink = permalinkRepository.findPermalinkByLongURL(longURL);
        if (existingPermalink != null) {
            return existingPermalink.getShortURL();
        }
        String md5DigestValue = DigestUtils.md5DigestAsHex(longURL.getBytes());
        int md5HashLength = md5DigestValue.length();
        int counter = 0;
        while(counter < md5HashLength-SHORT_URL_LENGTH){
            String id = md5DigestValue.substring(counter, counter+SHORT_URL_LENGTH);
            if(!permalinkRepository.existsById(id)) {
                Permalink link = new Permalink(id, id, longURL);
                permalinkRepository.save(link);
                return id;
            }
            counter++;
        }
        return longURL;
    }

    @Override
    public String redirectToLongURL(String shortURL) {
        Optional<Permalink> permalink = permalinkRepository.findById(shortURL);
        if(permalink.isEmpty())
            throw new URLNotFoundException("Invalid URL");
        return permalink.get().getLongURL();
    }

}
