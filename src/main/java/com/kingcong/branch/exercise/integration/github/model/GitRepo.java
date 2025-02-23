package com.kingcong.branch.exercise.integration.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GitRepo {
    private String name;
    @JsonProperty("html_url")
    private String htmlUrl;
}
