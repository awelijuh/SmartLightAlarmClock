package com.awelijuh.smartlightalarmclock.adapters.feign.toya.mappers;

import com.awelijuh.schemagenerator.dto.SchemaItem;
import com.awelijuh.schemagenerator.dto.TypeEnum;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaContainerDto;

import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.NoArgsConstructor;

@NoArgsConstructor(onConstructor = @__(@Inject))
public class ToyaMapper {

    public SchemaItem map(ToyaContainerDto dto) {
        var values = dto.getResult().getFunctions().stream().collect(Collectors.toMap(SchemaItem::getCode, Function.identity()));
        SchemaItem schemaItem = new SchemaItem();
        schemaItem.setCode("root");
        schemaItem.setType(TypeEnum.Json);
        schemaItem.setValues(values);
        return schemaItem;

    }

}
