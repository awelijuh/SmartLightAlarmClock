package com.awelijuh.smartlightalarmclock.core.ports.in;

public interface LedUseCase {

    Class<?> getCredentialsClass();

    Class<?> getDeviceClass();

    String getName();

    default String getKey() {
        return getName();
    }
}
