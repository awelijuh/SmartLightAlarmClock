package com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ToyaTokenRequestDto {

    @JsonProperty("grant_type")
    private Integer grantType;

}
