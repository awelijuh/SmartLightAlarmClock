package com.awelijuh.smartlightalarmclock.adapters.feign.toya.mappers;

import com.awelijuh.schemagenerator.dto.SchemaItem;
import com.awelijuh.schemagenerator.dto.TypeEnum;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaContainerDto;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaFunctionItemDto;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaFunctionsResultDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(onConstructor = @__(@Inject))
public class ToyaMapper {

    private final ToyaMapperBase toyaMapperBase = Mappers.getMapper(ToyaMapperBase.class);

    @Inject
    ObjectMapper objectMapper;

    @SneakyThrows
    private SchemaItem map(ToyaFunctionItemDto itemDto) {
        SchemaItem result = toyaMapperBase.map(itemDto);
        Map<String, Object> values = objectMapper.readValue(itemDto.getValues(), new TypeReference<Map<String, Object>>() {
        });
        if (itemDto.getType() == TypeEnum.Enum) {
            result.setRange(objectMapper.convertValue(values.get("range"), new TypeReference<List<String>>() {
            }));
        }
        if (itemDto.getType() == TypeEnum.Integer) {
            result.setMin(objectMapper.convertValue(values.get("min"), Integer.class));
            result.setMax(objectMapper.convertValue(values.get("max"), Integer.class));
            result.setScale(objectMapper.convertValue(values.get("scale"), Integer.class));
            result.setStep(objectMapper.convertValue(values.get("step"), Integer.class));
        }
        if (itemDto.getType() == TypeEnum.Json) {
            result.setValues(objectMapper.convertValue(values, new TypeReference<Map<String, SchemaItem>>() {
            }));
        }

        return result;
    }

    public SchemaItem map(ToyaContainerDto<ToyaFunctionsResultDto> dto) {
        var values = dto.getResult().getFunctions().stream().map(this::map).collect(Collectors.toMap(SchemaItem::getCode, Function.identity()));
        SchemaItem schemaItem = new SchemaItem();
        schemaItem.setCode("root");
        schemaItem.setType(TypeEnum.Json);
        schemaItem.setValues(values);
        return schemaItem;
    }

}
