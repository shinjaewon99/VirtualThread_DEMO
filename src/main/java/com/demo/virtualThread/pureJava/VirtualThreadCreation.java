package com.demo.virtualThread.pureJava;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VirtualThreadCreation {

    private static final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // 현재 쓰레드 출력
            log.info("1) run, thread: {}, class: {}", Thread.currentThread(), Thread.currentThread().getClass());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("2) run. thread: {}", Thread.currentThread());
        }
    };

    public static void main(String[] args) throws InterruptedException {

//        실제 쓰레드 실행
//        Thread thread = new Thread(runnable);
//        thread.setDaemon(true);
//        thread.start();
//        thread.join();

        Thread thread = Thread.ofVirtual().name("myVirtual").start(runnable);
        thread.join(); // Deamon 쓰레드는 join을 해줘야 된다.
    }
}
