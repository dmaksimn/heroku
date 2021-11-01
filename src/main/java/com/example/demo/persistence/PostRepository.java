package com.example.demo.persistence;

import com.example.demo.domain.entity.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
