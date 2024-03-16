package org.gateway.api.service.impl;

import okhttp3.OkHttpClient;
import org.gateway.api.exception.PaymentGatewayException;
import org.gateway.api.service.PaymentGateway;
import org.gateway.api.service.RequestBuilder;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

public class RetrofitPaymentGateway implements PaymentGateway {

    private final Retrofit retrofit;

    public RetrofitPaymentGateway(String baseUrl, String proxyHost, int proxyPort) {
        OkHttpClient okHttpClient = buildOkHttpClient(proxyHost, proxyPort);
        this.retrofit = buildRetrofit(baseUrl, okHttpClient);
    }

    private OkHttpClient buildOkHttpClient(String proxyHost, int proxyPort) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        return new OkHttpClient.Builder()
                .proxy(proxy)
                .connectionPool(new okhttp3.ConnectionPool())
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    private Retrofit buildRetrofit(String baseUrl, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public <T> T execute(RequestBuilder<T> request) {
        Call<T> call = request.buildCall(retrofit);
        try {
            retrofit2.Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new PaymentGatewayException("Failed to execute request: " + response.errorBody().string());
            }
        } catch (IOException e) {
            throw new PaymentGatewayException("Failed to execute request", e);
        }
    }
}
