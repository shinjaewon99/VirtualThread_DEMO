package com.demo.virtualThread.pureJava;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Pinned {

    private static final ReentrantLock lock = new ReentrantLock();

    private static final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            log.info("1) run. thread: {}", Thread.currentThread());

            // synchronize 나 native 안에서 sleep이 되어서 sleep이 언마운트 되지 못하므로 성능이 떨어짐
            // 코드블럭에서만 그런게 아니라 JDBC 이런데에서도 발생 할 수있음
            // -Djdk.tracePinnedThreads=full  or -Djdk.tracePinnedThreads=short 를 통해  detect
            // 빌드할때 cofing 설정을 걸어준다
//            synchronized (this) {
            lock.lock();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
            log.info("2) run. thread: {}", Thread.currentThread());
        }
//        }
    };

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        log.info("1) main. thread: " + Thread.currentThread());

//        platform();
        virtual();

        log.info("2) main. time: " + (System.currentTimeMillis() - startTime) + " , thread: " + Thread.currentThread());
    }

    private static void virtual() {
        ThreadFactory factory = Thread.ofVirtual().name("myVirtual-", 0).factory();
        try (ExecutorService executorService = Executors.newThreadPerTaskExecutor(factory)) {
            for (int i = 0; i < 20; i++) {
                Pinned pinning = new Pinned();
                executorService.submit(pinning.runnable);
            }
        }
    }

    private static void platform() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(20)) {
            for (int i = 0; i < 20; i++) {
                Pinned pinning = new Pinned();
                executorService.submit(pinning.runnable);
            }
        }
    }
}
