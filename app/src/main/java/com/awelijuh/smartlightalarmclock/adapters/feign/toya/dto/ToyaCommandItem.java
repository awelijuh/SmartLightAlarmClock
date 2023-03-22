package com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto;

import lombok.Data;

@Data
public class ToyaCommandItem<T> {

    private String code;

    private T value;
}
