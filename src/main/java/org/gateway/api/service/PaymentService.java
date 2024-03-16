package org.gateway.api.service;

import org.gateway.api.request.model.PaymentRequest;
import org.gateway.api.request.model.PaymentResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentService {
    @POST("payment")
    Call<PaymentResponse> processPayment(@Body PaymentRequest request);
}
