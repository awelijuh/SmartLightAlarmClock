package com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ToyaTokenResultDto {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expire_time")
    private Long expireTime;

    @JsonProperty("refresh_token")
    private String refreshToken;

    private String uid;

}
