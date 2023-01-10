package com.awelijuh.smartlightalarmclock.adapters.feign.toya;

import com.awelijuh.smartlightalarmclock.adapters.feign.toya.api.ToyaApi;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class ToyaFeignConfig {


    Interceptor authInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            return chain.proceed(request);
        }
    };

    @Singleton
    @Provides
    @Named("toya")
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build();
    }

    @Singleton
    @Provides
    @Named("toya")
    Retrofit provideRetrofit(@Named("toya") OkHttpClient okHttpClient, ObjectMapper objectMapper) {
        return new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(okHttpClient)
                .baseUrl("https://openapi.tuyaeu.com/")
                .build();
    }

    @Singleton
    @Provides
    ToyaApi provideToyaApi(@Named("toya") Retrofit retrofit) {
        return retrofit.create(ToyaApi.class);
    }

}
