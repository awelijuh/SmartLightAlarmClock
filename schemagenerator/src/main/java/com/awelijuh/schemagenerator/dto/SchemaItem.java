package com.awelijuh.schemagenerator.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SchemaItem {

    private String code;

    private String name;

    private TypeEnum type;

    private List<String> range;

    private Integer min;

    private Integer max;

    private Integer scale;

    private Integer step;

    private Map<String, SchemaItem> values;

}
