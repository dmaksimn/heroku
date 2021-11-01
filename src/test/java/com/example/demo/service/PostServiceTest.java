package com.example.demo.service;

import com.example.demo.domain.dto.PostDto;
import com.example.demo.domain.entity.Post;
import com.example.demo.file.FileManager;
import com.example.demo.persistence.PostImageRepository;
import com.example.demo.persistence.PostRepository;
import com.example.demo.service.converter.Converter;
import com.example.demo.service.converter.PostConverter;
import com.example.demo.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    private PostDto postDto = new PostDto();
    private Post post = new Post();

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostImageRepository postImageRepository;

    @Mock
    private FileManager fileManager;

    @Spy
    private Converter<Post, PostDto> converter = new PostConverter();

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    public void init() {
        post.setTitle("New");

    }

    @Test
    public void testPostSaveWithImage() {
        Mockito.when(postRepository.save(Mockito.any())).thenReturn(post);

        postDto.setImage(new byte[0]);
        PostDto result = postService.save(postDto);
        Assertions.assertEquals(result.getTitle(), "New");

        Mockito.verify(postRepository).save(Mockito.any());
        Mockito.verify(postImageRepository).save(Mockito.any());
        Mockito.verify(fileManager).saveFile(Mockito.any());
        Mockito.verify(converter).toDto(post);
        Mockito.verify(converter).toEntity(postDto);
    }

    @Test
    public void testFindPostById() {
        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        Mockito.when(postRepository.findById(2L)).thenReturn(Optional.ofNullable(null));

        PostDto result = postService.findById(1l);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getTitle(), "New");

        PostDto result2 = postService.findById(2l);
        Assertions.assertNull(result2);

        Mockito.verify(postRepository, Mockito.times(2)).findById(Mockito.any());
        Mockito.verify(converter).toDto(post);
    }
}
