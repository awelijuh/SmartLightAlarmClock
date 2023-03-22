package com.awelijuh.smartlightalarmclock.core.services;

import com.awelijuh.schemagenerator.GeneratedUI;
import com.awelijuh.schemagenerator.dto.SchemaItem;
import com.awelijuh.smartlightalarmclock.adapters.database.domain.Bulb;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.ToyaFeignAdapter;
import com.awelijuh.smartlightalarmclock.core.domain.toya.ToyaCredentials;
import com.awelijuh.smartlightalarmclock.core.domain.toya.ToyaDevice;
import com.awelijuh.smartlightalarmclock.core.domain.toya.led.ToyaLightPreference;
import com.awelijuh.smartlightalarmclock.core.ports.in.LedUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import lombok.NoArgsConstructor;

@NoArgsConstructor(onConstructor = @__(@Inject))
public class ToyaLedService implements LedUseCase<ToyaDevice> {

    SchemaItem schemaItem = new SchemaItem();

    @Inject
    ToyaFeignAdapter toyaFeignAdapter;

    @Inject
    ObjectMapper objectMapper;

    @Override
    public Class<?> getCredentialsClass() {
        return ToyaCredentials.class;
    }

    @Override
    public Class<ToyaDevice> getDeviceClass() {
        return ToyaDevice.class;
    }

    @Override
    public String getName() {
        return "Toya";
    }

    @Override
    public Single<SchemaItem> getSchemaControl(Bulb bulb) {
        return Single.just(GeneratedUI.mapClassToSchema(ToyaLightPreference.class));
    }

    @Override
    public Completable command(Bulb bulb, Map<String, Object> result) {
        return toyaFeignAdapter.command(getDevice(bulb).getDeviceId(), objectMapper.convertValue(result, ToyaLightPreference.class));
    }

}
