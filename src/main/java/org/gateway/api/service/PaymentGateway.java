package org.gateway.api.service;

public interface PaymentGateway {
    <T> T execute(RequestBuilder<T> request);
}
