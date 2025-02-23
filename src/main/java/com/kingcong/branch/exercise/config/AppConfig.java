package com.kingcong.branch.exercise.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

    @Bean(name = "restClientGit")
    RestClient restClientGit(@Value("${integration.github.url}") String githubUrl){
        return RestClient.builder().baseUrl(githubUrl).build();
    }
}
