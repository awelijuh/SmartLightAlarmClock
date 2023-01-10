package com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto;

import lombok.Data;

@Data
public class ToyaContainerDto<T> {

    private T result;

    private Boolean success;

    private Long t;

    private String tid;

}
