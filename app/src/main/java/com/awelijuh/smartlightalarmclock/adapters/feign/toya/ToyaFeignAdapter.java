package com.awelijuh.smartlightalarmclock.adapters.feign.toya;

import com.awelijuh.schemagenerator.dto.SchemaItem;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.api.ToyaApi;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.mappers.ToyaMapper;
import com.awelijuh.smartlightalarmclock.core.domain.toya.led.ToyaLightPreference;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import lombok.NoArgsConstructor;

@NoArgsConstructor(onConstructor = @__(@Inject))
public class ToyaFeignAdapter {

    @Inject
    ToyaApi toyaApi;

    @Inject
    ToyaMapper mapper;

    public Single<SchemaItem> getFunctions(String deviceId) {
        return toyaApi.getFunctions(deviceId).map(e -> mapper.map(e));
    }

    public Completable command(String deviceId, ToyaLightPreference preference) {
        return toyaApi.command(deviceId, mapper.mapCommand(preference));
    }

}
