package com.kingcong.branch.exercise.integration.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class GitUser {
    private String login;
    private String name;
    @JsonProperty("avatar_url")
    private String avatar;
    private String location;
    private String email;
    @JsonProperty("html_url")
    private String htmlUrl;
    @JsonProperty("created_at")
    private Date createdAt;
}
