package com.github.zipper2110.something.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class TaskExecutorConfig {

    @Bean
    public static TaskExecutor getTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}
