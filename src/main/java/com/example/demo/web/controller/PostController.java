package com.example.demo.web.controller;

import com.example.demo.domain.dto.PostDto;
import com.example.demo.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostDto> getAllPosts() {
        LOGGER.debug("Getting all the posts");
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        LOGGER.debug("Getting post with id [{}]", id);
        PostDto postDto = postService.findById(id);
        if (postDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(postDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        LOGGER.debug("Deleting post with id [{}]", id);
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        byte[] image = postService.findImageByPostId(id);
        if (image != null) {
            return ResponseEntity.ok(image);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Object> addPost(@RequestPart("post") @Valid PostDto post,
                                          @RequestPart("file") byte[] image) {
        LOGGER.debug("Adding new post [{}]", post);
        post.setImage(image);
        return ResponseEntity.ok(postService.save(post));
    }
}
