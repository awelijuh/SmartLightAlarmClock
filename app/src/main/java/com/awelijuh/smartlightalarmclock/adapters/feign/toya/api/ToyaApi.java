package com.awelijuh.smartlightalarmclock.adapters.feign.toya.api;

import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaContainerDto;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaFunctionsResultDto;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaTokenRequestDto;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaTokenResultDto;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;

public interface ToyaApi {

    @GET("v1.0/token")
    Single<ToyaContainerDto<ToyaTokenResultDto>> getToken(
            @Body ToyaTokenRequestDto requestDto,
            @HeaderMap Map<String, String> headers
    );

    @GET("v1.0/iot-03/devices/{deviceId}/functions")
    Single<ToyaContainerDto<ToyaFunctionsResultDto>> getFunctions(
            @Path("deviceId") String deviceId
    );

}
