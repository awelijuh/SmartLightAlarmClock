package com.awelijuh.smartlightalarmclock.core.domain.toya.led;

import com.awelijuh.schemagenerator.UiField;

import lombok.Data;

@Data
public class ToyaLightPreference {

    @UiField
    private Boolean switchLed;

    @UiField
    private WorkMode workMode;

    @UiField
    private ColourData colourData;

    @UiField(min = 0, max = 1000)
    private Integer bright;

    @UiField(min = 0, max = 1000)
    private Integer temp;

}
