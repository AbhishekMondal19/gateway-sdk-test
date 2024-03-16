package org.gateway.api.client;

import org.gateway.api.request.model.PaymentResponse;
import org.gateway.api.service.PaymentGateway;
import org.gateway.api.service.impl.PaymentRequestBuilder;
import org.gateway.api.service.impl.RetrofitPaymentGateway;

public class PaymentGatewayClient {
    private final PaymentGateway paymentGateway;

    public PaymentGatewayClient(String baseUrl, String proxyHost, int proxyPort) {
        this.paymentGateway = new RetrofitPaymentGateway(baseUrl, proxyHost, proxyPort);
    }

    public PaymentResponse processPayment(PaymentRequestBuilder requestBuilder) {
        return paymentGateway.execute(requestBuilder);
    }
}
