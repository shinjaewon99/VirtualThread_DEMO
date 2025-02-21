package com.demo.virtualThread.pureJava;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Slf4j
public class VirtualThreadExecutorCreation {

    private static final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            log.info("1) run. thread: {}", Thread.currentThread());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("2) run. thread: {}", Thread.currentThread());
        }
    };

    public static void main(String[] args) {
        log.info("1) main. thread: " + Thread.currentThread());


        // 해당 구문이 끝날때 자동으로 close 된다 try-with-resource
//        try(ExecutorService executorService = Executors.newFixedThreadPool(10)) {
//            for (int i = 0; i < 10; i++) {
//                executorService.submit(runnable);
//            }
//        }

        ThreadFactory factory = Thread.ofVirtual().name("myVirtual-", 0).factory();
        try (ExecutorService executorService = Executors.newThreadPerTaskExecutor(factory)) {
            for (int i = 0; i < 10; i++) {
                executorService.submit(runnable);
            }
        }

        log.info("2) main. thread: " + Thread.currentThread());
    }

    // 아래의 메소드처럼 가상쓰레드를 쓰레드 풀 처럼 사용하면 안된다.
    // 가상쓰레드는 가볍기 때문에 사용하고 버리고를 반복
    private static void antiPattern1() {
        ThreadFactory factory = Thread.ofVirtual().name("myVirtual-", 0).factory();
        try (ExecutorService executorService = Executors.newFixedThreadPool(1, factory)) {
            for (int i = 0; i < 10; i++) {
                executorService.submit(runnable);
            }
        }
    }
}
