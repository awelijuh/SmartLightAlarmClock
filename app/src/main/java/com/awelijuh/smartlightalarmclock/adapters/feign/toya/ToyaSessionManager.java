package com.awelijuh.smartlightalarmclock.adapters.feign.toya;

import static com.awelijuh.smartlightalarmclock.adapters.memory.PreferenceConstants.TOYA_ACCESS;

import android.content.SharedPreferences;

import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaContainerDto;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaTokenResultDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(onConstructor = @__(@Inject))
public class ToyaSessionManager {

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    ObjectMapper objectMapper;

    @SneakyThrows
    public void saveAccess(ToyaContainerDto<ToyaTokenResultDto> tokenResultDto) {
        ToyaTokenResultDto toyaTokenResultDto = tokenResultDto.getResult();
        toyaTokenResultDto.setExpireTime(tokenResultDto.getT() + toyaTokenResultDto.getExpireTime() * 1000);

        sharedPreferences.edit()
                .putString(TOYA_ACCESS, objectMapper.writeValueAsString(toyaTokenResultDto))
                .apply();
    }

    public void clearAccess() {
        sharedPreferences.edit()
                .putString(TOYA_ACCESS, "null")
                .apply();
    }

    @SneakyThrows
    private ToyaTokenResultDto getAccess() {
        return objectMapper.readValue(sharedPreferences.getString(TOYA_ACCESS, "null"), ToyaTokenResultDto.class);
    }

    @SneakyThrows
    public String getAccessToken() {
        var access = getAccess();
        if (access == null) {
            return null;
        }
        return getAccess().getAccessToken();
    }

    public String getRefreshToken() {
        var access = getAccess();
        if (access == null) {
            return null;
        }
        return access.getRefreshToken();
    }

    public boolean isAccessExists() {
        return getAccess() != null;
    }

    public boolean isAccessActive() {
        if (!isAccessExists()) {
            return false;
        }
        var access = getAccess();
        long now = System.currentTimeMillis();
        return access.getExpireTime() - 60_000 > now;
    }

}
