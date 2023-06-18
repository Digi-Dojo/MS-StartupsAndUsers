package com.startupsdigidojo.usersandteams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class UsersAndTeamsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersAndTeamsApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000", "http://localhost:9000/", "https://startup-digi-dojo-lab.onrender.com/", "https://mfront-startupsandusers.onrender.com", "https://startup-digi-dojo-lab-pr-3.onrender.com/");
            }
        };
    }
}
