package com.example.demo.domain.dto;

import javax.validation.constraints.NotBlank;

public class PostDto {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    private byte[] image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
