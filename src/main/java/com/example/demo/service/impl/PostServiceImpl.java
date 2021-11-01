package com.example.demo.service.impl;

import com.example.demo.domain.dto.PostDto;
import com.example.demo.domain.entity.Post;
import com.example.demo.domain.entity.PostImage;
import com.example.demo.file.FileManager;
import com.example.demo.persistence.PostImageRepository;
import com.example.demo.persistence.PostRepository;
import com.example.demo.service.PostService;
import com.example.demo.service.converter.Converter;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final FileManager fileManager;
    private final Converter<Post, PostDto> converter;

    public PostServiceImpl(PostRepository postRepository, PostImageRepository postImageRepository, FileManager fileManager, Converter<Post, PostDto> converter) {
        this.postRepository = postRepository;
        this.postImageRepository = postImageRepository;
        this.fileManager = fileManager;
        this.converter = converter;
    }

    @Override
    @Cacheable("posts")
    public PostDto findById(Long id) {
        return postRepository.findById(id).map(converter::toDto).orElse(null);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "posts", key = "#id"),
            @CacheEvict(value = "posts", key = "'all'")
    })
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    @Cacheable(value = "posts", key = "'all'")
    public List<PostDto> findAll() {
        return converter.toDto(Lists.newArrayList(postRepository.findAll()));
    }

    @Override
    @CacheEvict(value = "posts", allEntries = true)
    public PostDto save(PostDto postDto) {
        Post post = postRepository.save(converter.toEntity(postDto));
        if (postDto.getImage() != null) {
            String imageName = fileManager.saveFile(postDto.getImage());
            PostImage postImage = new PostImage();
            postImage.setPost(post);
            postImage.setImageName(imageName);
            postImageRepository.save(postImage);
        }
        return converter.toDto(post);
    }

    @Override
    public byte[] findImageByPostId(Long id) {
        return postImageRepository.findByPostId(id)
                .map(PostImage::getImageName)
                .map(fileManager::loadFile)
                .orElse(null);
    }


    /**
     * Method for test purposes
     */
    @Override
    @CacheEvict(value = "posts", allEntries = true)
    public void removeCache() {

    }
}
