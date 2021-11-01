package com.example.demo;

import com.example.demo.persistence.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class DemoApplicationTests {

    @Autowired
    private PostRepository postRepository;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(postRepository);
    }
}
