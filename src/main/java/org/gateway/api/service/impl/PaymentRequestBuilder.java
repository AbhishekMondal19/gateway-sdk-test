package org.gateway.api.service.impl;

import org.gateway.api.request.model.PaymentRequest;
import org.gateway.api.request.model.PaymentResponse;
import org.gateway.api.service.PaymentService;
import org.gateway.api.service.RequestBuilder;
import retrofit2.Call;
import retrofit2.Retrofit;

public class PaymentRequestBuilder implements RequestBuilder<PaymentResponse> {

    private final PaymentRequest request;

    public PaymentRequestBuilder(PaymentRequest request) {
        this.request = request;
    }

    @Override
    public Call<PaymentResponse> buildCall(Retrofit retrofit) {
        PaymentService service = retrofit.create(PaymentService.class);
        return service.processPayment(request);
    }
}
