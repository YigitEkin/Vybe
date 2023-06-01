package com.vybe.backend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vybe.backend.service.UserService;

import java.time.Duration;

@Service
public class SchedulerUtil {
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(1000);

    private final WebClient localApiClient;

    private final UserService userService;

    private volatile boolean isRunning = true;


    @Lazy
    @Autowired
    public SchedulerUtil(WebClient localApiClient, UserService userService) {
        this.localApiClient = localApiClient;
        this.userService = userService;
    }

    public void initializeVenue(Integer id) {
        isRunning = true;
        Thread loopThread = new Thread(() -> {
            while (isRunning) {
                        localApiClient
                .get()
                .uri(id + "/nextSongScheduled")
                .retrieve()
                .bodyToMono(String.class)
                .block(REQUEST_TIMEOUT);
            }
        });
        loopThread.start();
    }

    public void stop() {
        isRunning = false;
    }

    @Bean
    public WebClient localApiClient() {
        return WebClient.create("http://localhost:8080/api/scheduler/");
    }
}
