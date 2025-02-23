package com.kingcong.branch.exercise.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ApiUserData {
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("display_name")
    private String displayName;
    private String avatar;
    @JsonProperty("geo_location")
    private String geoLocation;
    private String email;
    private String url;
    @JsonProperty("created_at")
    private String createdAt;
    private List<ApiRepo> repos;
}

//created_at: "2011-01-25 18:44:36",