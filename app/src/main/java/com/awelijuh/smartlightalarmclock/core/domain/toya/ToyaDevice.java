package com.awelijuh.smartlightalarmclock.core.domain.toya;

import com.awelijuh.schemagenerator.UiField;

import lombok.Data;

@Data
public class ToyaDevice {

    @UiField(name = "device id")
    private String deviceId;

}
