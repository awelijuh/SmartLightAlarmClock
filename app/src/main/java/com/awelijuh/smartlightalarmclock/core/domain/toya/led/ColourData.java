package com.awelijuh.smartlightalarmclock.core.domain.toya.led;

import com.awelijuh.schemagenerator.UiField;

import lombok.Data;

@Data
public class ColourData {

    @UiField(min = 0, max = 360)
    private Integer h;

    @UiField(min = 0, max = 1000)
    private Integer s;

    @UiField(min = 0, max = 1000)
    private Integer v;

}
