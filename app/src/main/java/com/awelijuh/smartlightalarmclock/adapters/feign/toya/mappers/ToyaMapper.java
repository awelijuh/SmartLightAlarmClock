package com.awelijuh.smartlightalarmclock.adapters.feign.toya.mappers;

import com.awelijuh.schemagenerator.dto.SchemaItem;
import com.awelijuh.schemagenerator.dto.TypeEnum;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaCommandItem;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaCommandRoot;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaContainerDto;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaFunctionItemDto;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaFunctionsResultDto;
import com.awelijuh.smartlightalarmclock.core.domain.toya.led.ToyaLightPreference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(onConstructor = @__(@Inject))
public class ToyaMapper {

    public static final Set<String> CHARS = Set.of("range", "min", "max", "step", "values", "scale");

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
            var intersection = new HashSet<>(values.keySet());
            intersection.retainAll(CHARS);
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

    public ToyaCommandRoot mapCommand(ToyaLightPreference preference) {
        return new ToyaCommandRoot().setCommands(List.of(
                new ToyaCommandItem<>().setCode("switch_led").setValue(preference.getSwitchLed()),
                new ToyaCommandItem<>().setCode("work_mode").setValue(preference.getWorkMode()),
                new ToyaCommandItem<>().setCode("colour_data_v2").setValue(preference.getColourData()),
                new ToyaCommandItem<>().setCode("bright_value_v2").setValue(preference.getBright()),
                new ToyaCommandItem<>().setCode("temp_value_v2").setValue(preference.getTemp())
        ));
    }

}
