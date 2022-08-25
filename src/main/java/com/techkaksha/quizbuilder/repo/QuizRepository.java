package com.techkaksha.quizbuilder.repo;

import com.techkaksha.quizbuilder.model.Quiz;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long> {
    public Quiz findQuizByUserNameAndQuizId(String username, Long quizId);
    public List<Quiz> findQuizByUserName(String username, Pageable pageable);


    long countAllByUserName(String username);
}
