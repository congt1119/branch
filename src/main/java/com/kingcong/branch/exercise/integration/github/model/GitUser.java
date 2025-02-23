package com.kingcong.branch.exercise.integration.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GitUser {
    private String login;
    private String name;
    @JsonProperty("avatar_url")
    private String avatar;
    private String location;
    private String email;
    private String url;
    @JsonProperty("created_at")
    private String createdAt;
}

//created_at: "2011-01-25 18:44:36",