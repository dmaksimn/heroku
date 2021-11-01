package com.example.demo.service;

import com.example.demo.domain.dto.PostDto;
import com.example.demo.domain.entity.Post;
import com.example.demo.persistence.PostRepository;
import com.example.demo.service.converter.Converter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

@SpringBootTest
@ActiveProfiles("test")
public class PostServiceIntegrationTest {
    private static final Post post = new Post();
    @SpyBean
    private PostRepository postRepository;
    @Autowired
    private Converter<Post, PostDto> converter;
    @Autowired
    private PostService postService;

    @BeforeEach
    public void init() {
        post.setId(null);
        post.setTitle("New");
        post.setText("Some text");
        Mockito.clearInvocations(postRepository);
    }

    @Test
    public void testFindAfterSaveCache() {
        postService.removeCache();
        postService.findAll();
        postService.findById(1L);
        PostDto returned = postService.save(converter.toDto(post));
        postService.findAll();
        postService.findById(1L);

        PostDto postDto = postService.findById(returned.getId());
        Assertions.assertEquals(postDto.getTitle(), post.getTitle());

        Mockito.verify(postRepository, Mockito.times(2)).findAll();
        Mockito.verify(postRepository, Mockito.times(2)).findById(1L);

        postRepository.delete(post);
    }

    @Test
    public void testFindAllCache() {
        postService.removeCache();
        postService.findAll();
        postService.findAll();
        postService.findAll();

        Mockito.verify(postRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testFindByIdCache() {
        postService.removeCache();
        postService.findById(1L);
        postService.findById(1L);
        postService.findById(2L);

        Mockito.verify(postRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(postRepository, Mockito.times(1)).findById(2L);
    }

    @Test
    public void testFindByIdAfterDeleteCache() {
        PostDto toDelete = postService.save(converter.toDto(post));
        postService.findById(toDelete.getId());
        Assertions.assertNotNull(postService.findById(toDelete.getId()));
        postService.findById(2L);
        postService.delete(toDelete.getId());
        Assertions.assertNull(postService.findById(toDelete.getId()));
        postService.findById(2L);

        Mockito.verify(postRepository, Mockito.times(2)).findById(toDelete.getId());
        Mockito.verify(postRepository, Mockito.times(1)).findById(2L);
    }

    @Test
    public void testFindByAllAfterDeleteCache() {
        PostDto toDelete = postService.save(converter.toDto(post));
        postService.findAll();
        postService.findAll();
        List<PostDto> allBeforeDelete = postService.findAll();
        postService.delete(toDelete.getId());
        List<PostDto> allAfterDelete = postService.findAll();

        Assertions.assertEquals(1, allBeforeDelete.stream().filter(p -> Objects.equals(p.getId(), toDelete.getId())).count());
        Assertions.assertEquals(0, allAfterDelete.stream().filter(p -> Objects.equals(p.getId(), toDelete.getId())).count());
        Mockito.verify(postRepository, Mockito.times(2)).findAll();
    }
}
