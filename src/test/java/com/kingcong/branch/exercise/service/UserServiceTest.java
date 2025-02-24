package com.kingcong.branch.exercise.service;

import com.kingcong.branch.exercise.integration.github.GithubClient;
import com.kingcong.branch.exercise.integration.github.model.GitRepo;
import com.kingcong.branch.exercise.integration.github.model.GitUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;
    private GithubClient githubClient;

    @BeforeEach
    void setUp() {
        githubClient = Mockito.mock(GithubClient.class);
        userService = new UserService(githubClient);
    }

    @Test
    void getUserData() throws ExecutionException {
        List<GitRepo> repos = List.of(GitRepo.builder()
                .name("my_repo")
                .htmlUrl("http://fake-repo-url.com")
                .build());
        when(githubClient.getRepo("username")).thenReturn(repos);
        when(githubClient.getUser("username")).thenReturn(GitUser.builder()
                .login("username")
                .name("user")
                .location("usa")
                .avatar("http://fake-avatar-url.com")
                .email("user@example.com")
                .htmlUrl("http://fake-profile-url.com")
                .createdAt(Date.from(Instant.parse("2023-09-30T00:00:00Z")))
                .build());

        var result = userService.getUserData("username");

        assertNotNull(result);
        assertEquals("username", result.getUserName());
        assertEquals("user", result.getDisplayName());
        assertEquals("usa", result.getGeoLocation());
        assertEquals("http://fake-avatar-url.com", result.getAvatar());
        assertEquals("user@example.com", result.getEmail());
        assertEquals("http://fake-profile-url.com", result.getUrl());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        assertEquals("2023-09-30 00:00:00", simpleDateFormat.format(result.getCreatedAt()));
        assertEquals(1, result.getRepos().size());
        assertEquals("my_repo", result.getRepos().get(0).getName());
        assertEquals("http://fake-repo-url.com", result.getRepos().get(0).getUrl());
        verify(githubClient, times(1)).getUser("username");

        //Make another call to verify that the second call should hit the cache
        userService.getUserData("username");
        verify(githubClient, times(1)).getUser("username");
    }
}