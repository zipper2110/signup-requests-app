package com.github.zipper2110.something.configuration;

import com.github.zipper2110.something.messaging.MessagingService;
import com.github.zipper2110.something.messaging.MessagingServiceStub;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingServiceConfig {

    @Bean
    public static MessagingService getMessagingService() {
        return new MessagingServiceStub();
    }
}
