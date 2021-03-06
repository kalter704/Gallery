package com.yad.vasilii.gallery.di;

import com.squareup.picasso.*;
import com.yad.vasilii.gallery.BuildConfig;
import com.yad.vasilii.gallery.R;
import com.yad.vasilii.gallery.data.network.*;
import com.yad.vasilii.gallery.data.network.interceptor.*;

import android.content.*;
import android.os.*;

import java.util.*;

import javax.inject.*;

import dagger.*;
import okhttp3.*;
import okhttp3.logging.*;
import retrofit2.*;
import retrofit2.adapter.rxjava2.*;
import retrofit2.converter.gson.*;

@Module(includes = {AppModule.class})
public class NetworkModule {

    private OkHttpClient createClient(Interceptor... interceptors) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .cipherSuites(
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
                    ).build();
                builder.connectionSpecs(Collections.singletonList(spec));
        }
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(
                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        }
        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }
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
    NetworkChecker provideNetworkChecker(Context context) {
        return new NetworkChecker(context);
    }

    @Singleton
    @Provides
    ApiKeyInterceptor provideApiKeyInterceptor(@Named("api_key") String apiKey) {
        return new ApiKeyInterceptor(apiKey);
    }

    @Singleton
    @Provides
    NetworkCheckerInterceptor provideNetworkCheckerInterceptor(NetworkChecker networkChecker) {
        return new NetworkCheckerInterceptor(networkChecker);
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(ApiKeyInterceptor apiKeyInterceptor,
            NetworkCheckerInterceptor networkCheckerInterceptor) {
        return createClient(apiKeyInterceptor, networkCheckerInterceptor);
    }

    @Singleton
    @Provides
    OkHttp3Downloader provideOkHttp3Downloader(OkHttpClient client) {
        return new OkHttp3Downloader(client);
    }

    @Singleton
    @Provides
    PixabayApiService providePixabayApiService(OkHttpClient okHttpClient) {
        return createRetrofit(okHttpClient).create(PixabayApiService.class);
    }

    @Singleton
    @Provides
    Picasso providePicasso(Context context, OkHttp3Downloader downloader) {
        return new Picasso.Builder(context).downloader(downloader).build();
    }

}
