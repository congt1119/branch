package com.kingcong.branch.exercise.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kingcong.branch.exercise.api.model.ApiRepo;
import com.kingcong.branch.exercise.api.model.ApiUserData;
import com.kingcong.branch.exercise.integration.github.GithubClient;
import com.kingcong.branch.exercise.integration.github.model.GitRepo;
import com.kingcong.branch.exercise.integration.github.model.GitUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    //Simple cache to help with rate limit.
    //Size and duration could be moved to the configs. Keeping it here for simplicity
    private static final int MAX_CACHE_SIZE = 100;
    private static final int CACHE_EXPIRE_HOURS = 1;
    private final Cache<String, ApiUserData> cache = CacheBuilder.newBuilder()
            .maximumSize(MAX_CACHE_SIZE)
            .expireAfterAccess(CACHE_EXPIRE_HOURS, TimeUnit.HOURS)
            .build();

    private final GithubClient githubClient;

    public UserService(@Autowired GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    public ApiUserData getUserData(String user) throws ExecutionException {
        return cache.get(user, () -> {
            GitUser gitUser = githubClient.getUser(user);
            List<GitRepo> gitRepos = githubClient.getRepo(user);
            return constructUserData(gitUser, gitRepos);
        });
    }

    private static ApiUserData constructUserData(GitUser user, List<GitRepo> repos) {
        return ApiUserData.builder()
                .userName(user.getLogin())
                .displayName(user.getName())
                .avatar(user.getAvatar())
                .geoLocation(user.getLocation())
                .email(user.getEmail())
                .url(user.getHtmlUrl())
                .createdAt(user.getCreatedAt())
                .repos(repos.stream().map(r -> ApiRepo.builder().name(r.getName()).url(r.getHtmlUrl()).build()).toList())
                .build();
    }
}
