package com.daas.demo.dassDemo.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.util.Map;

@Component
@Slf4j
public class WebClientCalls {

    private final WebClient webClient;

    @Value("${http.notification.path}")
    private String notificationPath;

    @Value("${http.notification.host}")
    private String notificationHost;

    @Value("${notify.client_secret}")
    private String clientSecret;

    @Value("${notify.client_id}")
    private String clientId;

    public WebClientCalls(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(notificationHost)
                .build();
    }

    public String getPostById() {
        log.info("Call to notification api started");

        Map<String, String> body = Map.of(
                "client_secret", clientSecret,
                "client_id", clientId
        );

        try {
            return webClient.post()
                    .uri(notificationPath)
                    .header(HttpHeaders.ACCEPT, "application/json")
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(5)) // ⏱ Timeout handling
                    .block();
        } catch (WebClientRequestException e) {
            log.error("WebClient request failed: {}", e.getMessage(), e);
            throw new RuntimeException("External service is unreachable or timed out", e);
        } catch (WebClientResponseException e) {
            log.error("WebClient response error: status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw new RuntimeException("Received error response from external service", e);
        } catch (Exception e) {
            log.error("Unexpected error during WebClient call", e);
            throw new RuntimeException("Unexpected error during external call", e);
        }
    }
}
