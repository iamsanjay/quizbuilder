package com.techkaksha.quizbuilder.repo;

import com.techkaksha.quizbuilder.model.Permalink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermalinkRepository extends CrudRepository<Permalink, String> {
    Permalink findPermalinkByLongURL(String URL);
}
