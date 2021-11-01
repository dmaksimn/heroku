package com.example.demo.domain.entity;

import com.example.demo.domain.AuthenticationProvider;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private AuthenticationProvider provider;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthenticationProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthenticationProvider provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", provider=" + provider +
                '}';
    }
}
