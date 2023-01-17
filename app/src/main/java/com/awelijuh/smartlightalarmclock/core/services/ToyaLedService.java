package com.awelijuh.smartlightalarmclock.core.services;

import com.awelijuh.schemagenerator.dto.SchemaItem;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.ToyaFeignAdapter;
import com.awelijuh.smartlightalarmclock.core.domain.Light;
import com.awelijuh.smartlightalarmclock.core.domain.toya.ToyaCredentials;
import com.awelijuh.smartlightalarmclock.core.domain.toya.ToyaDevice;
import com.awelijuh.smartlightalarmclock.core.ports.in.LedUseCase;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import lombok.NoArgsConstructor;

@NoArgsConstructor(onConstructor = @__(@Inject))
public class ToyaLedService implements LedUseCase {

    @Inject
    ToyaFeignAdapter toyaFeignAdapter;

    @Override
    public Class<?> getCredentialsClass() {
        return ToyaCredentials.class;
    }

    @Override
    public Class<?> getDeviceClass() {
        return ToyaDevice.class;
    }

    @Override
    public String getName() {
        return "Toya";
    }

    @Override
    public Single<SchemaItem> getSchemaControl(Light light) {
        return toyaFeignAdapter.getFunctions(((ToyaDevice) light.getDevice()).getDeviceId());
    }


}
