package com.kingcong.branch.exercise.service;

import com.kingcong.branch.exercise.api.model.ApiRepo;
import com.kingcong.branch.exercise.api.model.ApiUserData;
import com.kingcong.branch.exercise.integration.github.GithubClient;
import com.kingcong.branch.exercise.integration.github.model.GitRepo;
import com.kingcong.branch.exercise.integration.github.model.GitUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final GithubClient githubClient;

    public UserService(@Autowired GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    public ApiUserData getUserData(String user){
        GitUser gitUser = githubClient.getUser(user);
        List<GitRepo> gitRepos = githubClient.getRepo(user);
        return constructUserData(gitUser, gitRepos);
    }

    private static ApiUserData constructUserData(GitUser user, List<GitRepo> repos){
        return ApiUserData.builder()
                .userName(user.getLogin())
                .displayName(user.getName())
                .avatar(user.getAvatar())
                .geoLocation(user.getLocation())
                .email(user.getEmail())
                .url(user.getUrl())
                .createdAt(user.getCreatedAt())
                .repos(repos.stream().map(r -> ApiRepo.builder().name(r.getName()).url(r.getHtmlUrl()).build()).toList())
                .build();
    }
}
