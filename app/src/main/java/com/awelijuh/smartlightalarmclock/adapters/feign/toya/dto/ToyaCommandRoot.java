package com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto;

import java.util.List;

import lombok.Data;

@Data
public class ToyaCommandRoot {
    private List<ToyaCommandItem<Object>> commands;
}
