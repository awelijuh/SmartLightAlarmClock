package com.awelijuh.smartlightalarmclock.core.services;

import com.awelijuh.schemagenerator.dto.SchemaItem;
import com.awelijuh.smartlightalarmclock.adapters.database.domain.Bulb;
import com.awelijuh.smartlightalarmclock.core.domain.toya.ToyaDevice;
import com.awelijuh.smartlightalarmclock.core.ports.in.LedUseCase;

import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;


//@NoArgsConstructor(onConstructor = @__(@Inject))
public class MiLedService implements LedUseCase<ToyaDevice> {

    @Override
    public Class<?> getCredentialsClass() {
        return null;
    }

    @Override
    public Class<ToyaDevice> getDeviceClass() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Single<SchemaItem> getSchemaControl(Bulb bulb) {
        return null;
    }

    @Override
    public Completable command(Bulb bulb, Map<String, Object> result) {
        return null;
    }
}
