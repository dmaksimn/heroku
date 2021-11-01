package com.example.demo.service.converter;

import com.example.demo.domain.dto.PostDto;
import com.example.demo.domain.entity.Post;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostConverter implements Converter<Post, PostDto> {
    @Override
    public Post toEntity(PostDto postDto) {
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setText(postDto.getText());
        return post;
    }

    @Override
    public PostDto toDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setText(post.getText());
        return dto;
    }

    @Override
    public List<Post> toEntity(List<PostDto> postDtos) {
        return postDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> toDto(List<Post> posts) {
        return posts.stream().map(this::toDto).collect(Collectors.toList());
    }
}
