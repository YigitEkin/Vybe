package com.vybe.backend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class SchedulerUtil {
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(1000);

    private final WebClient localApiClient;

    @Lazy
    @Autowired
    public SchedulerUtil(WebClient localApiClient) {
        this.localApiClient = localApiClient;
    }

    public void initializeVenue(Integer id) {
        while (true)
        {
            localApiClient
                .get()
                .uri(id + "/nextSongScheduled")
                .retrieve()
                .bodyToMono(String.class)
                .block(REQUEST_TIMEOUT);
        }
    }

    @Bean
    public WebClient localApiClient() {
        return WebClient.create("http://localhost:8080/api/scheduler/");
    }
}
