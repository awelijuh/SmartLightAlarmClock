package com.awelijuh.smartlightalarmclock.core.domain.toya;

import com.awelijuh.schemagenerator.UiField;

import lombok.Data;

@Data
public class ToyaCredentials {

    @UiField(name = "access id")
    private String accessId;

    @UiField(name = "access key")
    private String accessKey;

}
