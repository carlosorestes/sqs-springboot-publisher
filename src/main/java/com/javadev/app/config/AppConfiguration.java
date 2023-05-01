package com.javadev.app.config;

import com.javadev.app.service.MessageQueueService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class AppConfiguration {

    private final MessageQueueService messageQueueService;

    @PostConstruct
    public void initializeMessageQueue() {
        messageQueueService.createMessageQueue();
    }
}
