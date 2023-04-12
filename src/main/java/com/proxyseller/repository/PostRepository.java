package com.proxyseller.repository;

import com.proxyseller.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    @Query(value = "{}", sort = "{ dateAdded : -1 }")
    List<Post> findAllByOrderByDateAddedDesc(Pageable pageable);
}
