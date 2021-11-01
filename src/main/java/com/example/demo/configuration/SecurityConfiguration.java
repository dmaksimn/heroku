package com.example.demo.configuration;

import com.example.demo.configuration.properties.CorsProperties;
import com.example.demo.domain.AuthenticationProvider;
import com.example.demo.domain.entity.User;
import com.example.demo.persistence.UserRepository;
import com.example.demo.web.CustomBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserRepository userRepository;
    private final CorsProperties corsProperties;
    private final CustomBasicAuthenticationEntryPoint entryPoint;

    public SecurityConfiguration(CustomBasicAuthenticationEntryPoint entryPoint, CorsProperties corsProperties,
                                 UserRepository userRepository) {
        this.entryPoint = entryPoint;
        this.corsProperties = corsProperties;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/oauth").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .httpBasic().authenticationEntryPoint(entryPoint)
                .and()
                .oauth2Login().successHandler(new CustomAuthenticationSuccessHandler())
                .and()
                .logout().logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication().passwordEncoder(passwordEncoder())
                .withUser("user")
                .password(passwordEncoder().encode("12345678"))
                .roles("USER");
    }

    private class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            User user = new User();
            user.setUsername(oAuth2User.getAttribute("email"));
            user.setProvider(AuthenticationProvider.GOOGLE);
            userRepository.save(user);
            httpServletResponse.sendRedirect(corsProperties.getUiUrl());
        }
    }
}
