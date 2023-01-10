package com.awelijuh.smartlightalarmclock.core.domain;

import java.util.UUID;

import lombok.Data;

@Data
public class Light {

    private String id = UUID.randomUUID().toString();

    private String name;

    private String type;

    private Object device;

}
