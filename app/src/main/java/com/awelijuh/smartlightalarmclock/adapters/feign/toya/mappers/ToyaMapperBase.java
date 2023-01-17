package com.awelijuh.smartlightalarmclock.adapters.feign.toya.mappers;

import com.awelijuh.schemagenerator.dto.SchemaItem;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaFunctionItemDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ToyaMapperBase {

    @Mapping(target = "values", ignore = true)
    SchemaItem map(ToyaFunctionItemDto itemDto);

}
