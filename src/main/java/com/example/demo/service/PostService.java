package com.example.demo.service;

import com.example.demo.domain.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto findById(Long id);

    void delete(Long id);

    List<PostDto> findAll();

    byte[] findImageByPostId(Long id);

    PostDto save(PostDto object);

    /**
     * Method for test purposes
     */
    void removeCache();
}
