package com.example.demo.persistence;

import com.example.demo.domain.entity.PostImage;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostImageRepository extends CrudRepository<PostImage, Long> {
    Optional<PostImage> findByPostId(Long postId);
}
