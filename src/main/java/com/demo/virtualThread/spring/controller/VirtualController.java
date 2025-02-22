package com.demo.virtualThread.spring.controller;

import com.demo.virtualThread.spring.service.VirtualService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VirtualController {
    private final VirtualService virtualService;
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/sleep")
    public void sleep() throws InterruptedException {

        // 위와 아래는 동일한 가상 쓰레드지만, cpu 할당에 따라 플랫폼 쓰레드가 다를 수 있다.
        log.info("1) counter: {}, thread: {}", counter.incrementAndGet(), Thread.currentThread());
        Thread.sleep(5000);
        log.info("2) thread: {}", Thread.currentThread());
    }

    @GetMapping("/async")
    public void async() {
        log.info("1) async. thread: {}", Thread.currentThread());
        virtualService.async();
        log.info("2) async. thread: {}", Thread.currentThread());
    }
}
