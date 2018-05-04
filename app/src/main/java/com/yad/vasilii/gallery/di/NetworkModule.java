package com.yad.vasilii.gallery.di;

import com.yad.vasilii.gallery.*;
import com.yad.vasilii.gallery.data.network.*;

import android.content.*;

import javax.inject.*;

import dagger.*;
import okhttp3.*;
import okhttp3.logging.*;
import retrofit2.*;
import retrofit2.adapter.rxjava2.*;
import retrofit2.converter.gson.*;

@Module(includes = {AppModule.class})
public class NetworkModule {

    private OkHttpClient createClient(ApiKeyInterceptor apiKeyInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(
                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        }
        builder.addInterceptor(apiKeyInterceptor);
        return builder.build();
    }

    private Retrofit createRetrofit(OkHttpClient client) {
        return new Retrofit.Builder().baseUrl(PixabayApiService.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).client(client).build();
    }

    @Named("api_key")
    @Singleton
    @Provides
    String provideApiKey(Context context) {
        return context.getString(R.string.api_key);
    }

    @Singleton
    @Provides
    ApiKeyInterceptor provideApiKeyInterceptor(@Named("api_key") String apiKey) {
        return new ApiKeyInterceptor(apiKey);
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(ApiKeyInterceptor apiKeyInterceptor) {
        return createClient(apiKeyInterceptor);
    }

    @Singleton
    @Provides
    PixabayApiService providePixabayApiService(OkHttpClient okHttpClient) {
        return createRetrofit(okHttpClient).create(PixabayApiService.class);
    }

}
