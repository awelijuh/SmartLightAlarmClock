package com.awelijuh.smartlightalarmclock.adapters.feign.toya;

import android.content.SharedPreferences;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;

import lombok.NoArgsConstructor;

@NoArgsConstructor(onConstructor = @__(@Inject))
public class ToyaSessionManager {

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    ObjectMapper objectMapper;



}
