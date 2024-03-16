package org.gateway.api.service;

import retrofit2.Call;
import retrofit2.Retrofit;

public interface RequestBuilder<T> {
    Call<T> buildCall(Retrofit retrofit);
}
