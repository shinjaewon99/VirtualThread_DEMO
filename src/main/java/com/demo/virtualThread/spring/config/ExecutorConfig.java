package com.demo.virtualThread.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.task.SimpleAsyncTaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@Slf4j
public class ExecutorConfig {


    // Executor 에서는 @Primary 대신 Bean의 이름이 taskExecutor이면 그 메소드가 우선순위가 제일 높다
    // default가 됨
    @Bean
    public SimpleAsyncTaskExecutor taskExecutor(SimpleAsyncTaskExecutorBuilder builder) {
        return builder.build();
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setThreadNamePrefix("myThreadPool-");
        // TODO: more setting...
        return threadPoolTaskExecutor;
    }

}
