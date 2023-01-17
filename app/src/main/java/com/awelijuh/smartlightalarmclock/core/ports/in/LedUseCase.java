package com.awelijuh.smartlightalarmclock.core.ports.in;

import com.awelijuh.schemagenerator.dto.SchemaItem;
import com.awelijuh.smartlightalarmclock.core.domain.Light;

import io.reactivex.rxjava3.core.Single;

public interface LedUseCase {

    Class<?> getCredentialsClass();

    Class<?> getDeviceClass();

    String getName();

    default String getKey() {
        return getName();
    }

    Single<SchemaItem> getSchemaControl(Light light);
}
