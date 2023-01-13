package com.awelijuh.smartlightalarmclock.adapters.feign.toya;

import android.content.SharedPreferences;

import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaTokenResultDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import static com.awelijuh.smartlightalarmclock.adapters.memory.PreferenceConstants.TOYA_ACCESS;

@NoArgsConstructor(onConstructor = @__(@Inject))
public class ToyaSessionManager {

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    ObjectMapper objectMapper;

    @SneakyThrows
    public void saveAccess(ToyaTokenResultDto tokenResultDto) {
        sharedPreferences.edit()
                .putString(TOYA_ACCESS, objectMapper.writeValueAsString(tokenResultDto))
                .apply();
    }

    @SneakyThrows
    public ToyaTokenResultDto getAccess() {
        return objectMapper.readValue(sharedPreferences.getString(TOYA_ACCESS, null), ToyaTokenResultDto.class);
    }


}
