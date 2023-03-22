package com.awelijuh.smartlightalarmclock.core.ports.in;

import com.awelijuh.schemagenerator.dto.SchemaItem;
import com.awelijuh.smartlightalarmclock.adapters.database.domain.Bulb;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import lombok.SneakyThrows;

public interface LedUseCase<Device> {

    Class<?> getCredentialsClass();

    Class<Device> getDeviceClass();

    String getName();

    default String getKey() {
        return getName();
    }

    Single<SchemaItem> getSchemaControl(Bulb bulb);

    Completable command(Bulb bulb, Map<String, Object> result);

    @SneakyThrows
    default Device getDevice(Bulb bulb) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (bulb.getDevice() == null) {
            return null;
        }
        return objectMapper.readValue(bulb.getDevice(), getDeviceClass());
    }
}
