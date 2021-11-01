package com.example.demo.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    @Profile("!ehcache")
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("posts");
    }
}
