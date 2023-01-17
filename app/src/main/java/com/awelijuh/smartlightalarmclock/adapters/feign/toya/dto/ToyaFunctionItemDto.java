package com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto;

import com.awelijuh.schemagenerator.dto.TypeEnum;

import java.util.List;

import lombok.Data;

@Data
public class ToyaFunctionItemDto {

    private String code;

    private String name;

    private TypeEnum type;

    private String values;

}
