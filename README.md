# gateway-sdk-test
Certainly! Below is a professional documentation for incorporating the Payment Gateway SDK into a calling application:

---

# Payment Gateway SDK Documentation

## Introduction

The Payment Gateway SDK is a Java library designed to simplify integration with various payment gateways in Java applications. It provides a set of interfaces, classes, and utilities to facilitate communication with payment gateways, handle exceptions, and ensure resilience in the face of network failures and other issues.

## Features

- Integration with Retrofit for making HTTP requests to payment gateways.
- Support for Resilience4j circuit breaker for resilience and fault tolerance.
- Custom exception handling to handle network failures, HTTP errors, and business-specific errors from payment gateways.
- Flexible configuration options using Spring Boot's application.properties file.

## Getting Started

To incorporate the Payment Gateway SDK into your Java application, follow these steps:

### Step 1: Add Dependencies

Add the following dependencies to your project's `pom.xml` file:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-resilience4j</artifactId>
</dependency>
<dependency>
    <groupId>io.github.resilience4j</groupId>
    <artifactId>resilience4j-spring-boot2</artifactId>
    <version>1.7.0</version>
</dependency>
```

### Step 2: Configure Circuit Breaker

Configure circuit breaker properties in your application.properties file:

```properties
resilience4j.circuitbreaker.configs.default.baseConfig.slidingWindowSize=10
resilience4j.circuitbreaker.configs.default.baseConfig.minimumNumberOfCalls=5
resilience4j.circuitbreaker.configs.default.baseConfig.permittedNumberOfCallsInHalfOpenState=3
resilience4j.circuitbreaker.configs.default.baseConfig.waitDurationInOpenState=5s
resilience4j.circuitbreaker.configs.default.baseConfig.failureRateThreshold=50
resilience4j.circuitbreaker.configs.default.baseConfig.recordExceptions[0]=java.io.IOException
```

### Step 3: Implement Payment Gateway Client

Create a `PaymentGatewayClient` class to interact with the Payment Gateway SDK. Annotate the `processPayment` method with `@CircuitBreaker` to enable circuit breaker functionality.

```java
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.stereotype.Component;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Component
public class PaymentGatewayClient {
    private final PaymentGateway paymentGateway;
    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;

    public PaymentGatewayClient(PaymentGateway paymentGateway, Resilience4JCircuitBreakerFactory circuitBreakerFactory) {
        this.paymentGateway = paymentGateway;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @CircuitBreaker(name = "processPayment", fallbackMethod = "fallbackProcessPayment")
    public <T> T processPayment(RequestBuilder<T> request, Class<T> responseType) {
        return paymentGateway.execute(request, responseType);
    }

    public <T> T fallbackProcessPayment(RequestBuilder<T> request, Class<T> responseType, Exception ex) {
        // Provide fallback implementation here
    }
}
```

### Step 4: Usage in Calling Application

Inject the `PaymentGatewayClient` bean into your Spring Boot components and use it to call the `processPayment` method.

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @Autowired
    private PaymentGatewayClient paymentGatewayClient;

    @GetMapping("/processPayment")
    public String processPayment() {
        // Call processPayment method
        // This method will be wrapped with a circuit breaker
        // If it fails, the fallback method will be invoked
        return paymentGatewayClient.processPayment(/* pass request builder and response type */);
    }
}
```

## Conclusion

The Payment Gateway SDK provides a robust and flexible solution for integrating with payment gateways in Java applications. By following the steps outlined in this documentation, developers can easily incorporate the SDK into their applications, benefitting from its resilience, fault tolerance, and exception handling capabilities.

For more information and detailed usage instructions, refer to the SDK's documentation and codebase.

---

This documentation provides a comprehensive guide for developers to understand and incorporate the Payment Gateway SDK into their applications. It covers the installation steps, configuration options, implementation details, and usage examples, ensuring a smooth integration process for developers.

Certainly! Let's enhance the documentation to include the usage of important interfaces and classes inside the Payment Gateway SDK along with some details about each:

---

# Payment Gateway SDK Documentation

## Introduction

The Payment Gateway SDK is a Java library designed to simplify integration with various payment gateways in Java applications. It provides a set of interfaces, classes, and utilities to facilitate communication with payment gateways, handle exceptions, and ensure resilience in the face of network failures and other issues.

## Features

- Integration with Retrofit for making HTTP requests to payment gateways.
- Support for Resilience4j circuit breaker for resilience and fault tolerance.
- Custom exception handling to handle network failures, HTTP errors, and business-specific errors from payment gateways.
- Flexible configuration options using Spring Boot's application.properties file.

## Important Interfaces and Classes

### 1. PaymentGateway

- **Description**: This interface defines the contract for interacting with payment gateways. It contains a single method `execute` for executing requests to the payment gateway.

- **Usage**: Implement this interface to create custom payment gateway implementations. Use the `execute` method to execute requests to the payment gateway.

### 2. RequestBuilder

- **Description**: This interface defines the contract for building requests to be sent to payment gateways. It contains a method `buildCall` to build the Retrofit Call object.

- **Usage**: Implement this interface to create custom request builders for different types of requests to the payment gateway. Use the `buildCall` method to build the Retrofit Call object.

### 3. RetrofitPaymentGateway

- **Description**: This class is an implementation of the PaymentGateway interface using Retrofit for making HTTP requests.

- **Usage**: Use this class as a default implementation of the PaymentGateway interface. Inject it into the PaymentGatewayClient to interact with payment gateways using Retrofit.

### 4. PaymentGatewayClient

- **Description**: This class acts as a client for interacting with payment gateways. It wraps the PaymentGateway interface and provides methods for executing payment requests.

- **Usage**: Inject this class into your Spring Boot components to interact with payment gateways. Use its methods, such as `processPayment`, to execute payment requests.

### 5. PaymentGatewayException

- **Description**: This class represents an exception that may occur during communication with payment gateways. It provides details such as status code and response body.

- **Usage**: Handle instances of this exception to manage errors gracefully in your application. Inspect its properties, such as status code and response body, to determine the cause of the error.

## Getting Started

To incorporate the Payment Gateway SDK into your Java application, follow these steps:

...

## Conclusion

The Payment Gateway SDK provides a robust and flexible solution for integrating with payment gateways in Java applications. By following the steps outlined in this documentation and understanding the usage of important interfaces and classes, developers can easily incorporate the SDK into their applications, benefitting from its resilience, fault tolerance, and exception handling capabilities.

For more information and detailed usage instructions, refer to the SDK's documentation and codebase.

---

This documentation provides a comprehensive guide for developers to understand and incorporate the Payment Gateway SDK into their applications. It covers the installation steps, configuration options, implementation details, and usage examples, ensuring a smooth integration process for developers. Additionally, it explains the usage of important interfaces and classes within the SDK, providing clarity on their roles and functionalities.