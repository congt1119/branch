package com.kingcong.branch.exercise.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@JsonInclude
@Builder
@Getter
public class ApiRepo {
    private String name;
    private String url;
}
