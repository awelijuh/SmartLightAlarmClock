package com.awelijuh.smartlightalarmclock.adapters.feign.toya;

import com.awelijuh.smartlightalarmclock.adapters.feign.toya.api.ToyaApi;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaContainerDto;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaTokenResultDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class ToyaFeignConfig {

    private static final String BASE_URL = "https://openapi.tuyaeu.com/";


    @Provides
    @Named("toya")
    Interceptor provideTokenInterceptor(ToyaSessionManager toyaSessionManager, ObjectMapper objectMapper) {
        return chain -> {
            Request request = chain.request();

            if (!toyaSessionManager.isAccessActive()) {
                Request.Builder tokenRequestBuilder = new Request.Builder()
                        .get()
                        .url(BASE_URL + "v1.0/token" + (!toyaSessionManager.isAccessExists() ? "?grant_type=1" : "/" + toyaSessionManager.getRefreshToken()));
                String tokenResult = chain.proceed(tokenRequestBuilder.build()).body().string();
                var access = objectMapper.readValue(tokenResult, new TypeReference<ToyaContainerDto<ToyaTokenResultDto>>() {
                });
                toyaSessionManager.saveAccess(access);
            }

            return chain.proceed(request);
        };
    }

    @Provides
    @Named("toya_sign")
    Interceptor toyaSignInterceptor(ToyaSessionManager toyaSessionManager, RequestSignUtils requestSignUtils) {
        return chain -> {
            Request request = chain.request();
            String accessToken = toyaSessionManager.getAccessToken();
            if (request.url().encodedPath().startsWith("/v1.0/token/")) {
                accessToken = "";
            }
            var headers = requestSignUtils.getHeader(accessToken, request, "", Map.of());
            request = request.newBuilder()
                    .headers(headers)
                    .build();
            return chain.proceed(request);
        };
    }

    @Provides
    @Named("toya")
    OkHttpClient provideOkHttpClient(@Named("toya") Interceptor toyaTokenInterceptor, @Named("toya_sign") Interceptor signInterceptor) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(toyaTokenInterceptor)
                .addInterceptor(signInterceptor)
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Named("toya")
    Retrofit provideRetrofit(@Named("toya") OkHttpClient okHttpClient, ObjectMapper objectMapper) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .build();
    }

    @Provides
    ToyaApi provideToyaApi(@Named("toya") Retrofit retrofit) {
        return retrofit.create(ToyaApi.class);
    }

}
