package com.awelijuh.smartlightalarmclock.adapters.feign.toya;

import com.awelijuh.smartlightalarmclock.adapters.feign.toya.api.ToyaApi;
import com.awelijuh.smartlightalarmclock.adapters.feign.toya.dto.ToyaTokenResultDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class ToyaFeignConfig {


    @Provides
    @Named("toya")
    Interceptor provideTokenInterceptor(ToyaSessionManager toyaSessionManager) {
        return chain -> {
            Request request = chain.request();


            ToyaTokenResultDto tokenResultDto = toyaSessionManager.getAccess();

            if (tokenResultDto == null) {

            }

            return chain.proceed(request);
        };
    }

    @Provides
    @Named("toya")
    OkHttpClient provideOkHttpClient(@Named("toya") Interceptor toyaTokenInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(toyaTokenInterceptor)
                .build();
    }

    @Provides
    @Named("toya")
    Retrofit provideRetrofit(@Named("toya") OkHttpClient okHttpClient, ObjectMapper objectMapper) {
        return new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(okHttpClient)
                .baseUrl("https://openapi.tuyaeu.com/")
                .build();
    }

    @Provides
    ToyaApi provideToyaApi(@Named("toya") Retrofit retrofit) {
        return retrofit.create(ToyaApi.class);
    }

}
