package com.cynefin.videostream.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ThreadPoolConfig {

    @Bean(name = "httpThreadPool")
    public Executor httpThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // Minimum number of threads
        executor.setMaxPoolSize(20); // Maximum number of threads
        executor.setQueueCapacity(100); // Maximum number of requests in the queue
        executor.setThreadNamePrefix("HttpThreadPool-");
        executor.initialize();
        return executor;
    }
}
