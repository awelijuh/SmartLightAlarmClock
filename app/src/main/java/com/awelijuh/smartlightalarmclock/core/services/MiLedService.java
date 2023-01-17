package com.awelijuh.smartlightalarmclock.core.services;

import com.awelijuh.schemagenerator.dto.SchemaItem;
import com.awelijuh.smartlightalarmclock.core.domain.Light;
import com.awelijuh.smartlightalarmclock.core.ports.in.LedUseCase;

import io.reactivex.rxjava3.core.Single;


//@NoArgsConstructor(onConstructor = @__(@Inject))
public class MiLedService implements LedUseCase {

    @Override
    public Class<?> getCredentialsClass() {
        return null;
    }

    @Override
    public Class<?> getDeviceClass() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Single<SchemaItem> getSchemaControl(Light light) {
        return null;
    }
}
