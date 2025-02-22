package com.demo.virtualThread.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.task.SimpleAsyncTaskSchedulerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@Slf4j
public class SchedulerConfig {


    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setThreadNamePrefix("myScheduler-");
        threadPoolTaskScheduler.setPoolSize(10);
        // TODO: more settings...
        return threadPoolTaskScheduler;
    }


    // 가상 쓰레드용 메소드
    // SimpleAsyncTaskSchedulerBuilder : 가상 쓰레드가 설정되어있는 빌더
    @Primary // default로 사용하기 위한 어노테이션
    @Bean
    public SimpleAsyncTaskScheduler simpleAsyncTaskScheduler(SimpleAsyncTaskSchedulerBuilder builder) {
        return builder.build();
    }

}
