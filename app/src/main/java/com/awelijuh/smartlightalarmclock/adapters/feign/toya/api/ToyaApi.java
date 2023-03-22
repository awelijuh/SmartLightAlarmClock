package com.awelijuh.smartlightalarmclock.adapters.feign.toya.api;

import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaCommandRoot;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaContainerDto;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaFunctionsResultDto;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaTokenResultDto;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ToyaApi {

    @GET("v1.0/token")
    Single<ToyaContainerDto<ToyaTokenResultDto>> getToken();

    @GET("v1.0/iot-03/devices/{deviceId}/functions")
    Single<ToyaContainerDto<ToyaFunctionsResultDto>> getFunctions(
            @Path("deviceId") String deviceId
    );

    @GET("v1.0/iot-03/devices/{deviceId}/status")
    Single<ToyaCommandRoot> getStatus(@Path("deviceId") String deviceId);

    @POST("v1.0/devices/{deviceId}/commands")
    Completable command(@Path("deviceId") String deviceId, @Body ToyaCommandRoot commandRoot);

}
