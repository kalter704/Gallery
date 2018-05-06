package com.yad.vasilii.gallery.data.network.interceptor;

import com.yad.vasilii.gallery.data.network.*;
import com.yad.vasilii.gallery.domain.excetions.*;

import java.io.*;

import okhttp3.*;

public class NetworkCheckerInterceptor implements Interceptor {

    private final NetworkChecker mNetworkChecker;

    public NetworkCheckerInterceptor(NetworkChecker networkChecker) {
        mNetworkChecker = networkChecker;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        if (!mNetworkChecker.isConnected()) {
            throw new NoNetworkException();
        }
        return chain.proceed(requestBuilder.build());
    }
}
