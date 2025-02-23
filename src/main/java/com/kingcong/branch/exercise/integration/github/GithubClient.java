package com.kingcong.branch.exercise.integration.github;

import com.kingcong.branch.exercise.integration.github.model.GitRepo;
import com.kingcong.branch.exercise.integration.github.model.GitUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class GithubClient {

    private final RestClient restClient;

    public GithubClient(@Autowired @Qualifier("restClientGit") RestClient restClient){
        this.restClient=restClient;
    }

    public GitUser getUser(String user){
        return restClient.get().uri("/users/{user}", user).retrieve().body(GitUser.class);
    }

    public List<GitRepo> getRepo(String user){
        return restClient.get().uri("/users/{user}/repos", user).retrieve().body(new ParameterizedTypeReference<>() {
        });
    }

}
