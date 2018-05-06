package com.yad.vasilii.gallery.data.network.interceptor;

import java.io.*;

import okhttp3.*;

public class ApiKeyInterceptor implements Interceptor {

    private final String mApiKey;

    public ApiKeyInterceptor(String apiKey) {
        mApiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl url = original.url().newBuilder().addQueryParameter("key", mApiKey).build();
        return chain.proceed(original.newBuilder().url(url).build());
    }
}
