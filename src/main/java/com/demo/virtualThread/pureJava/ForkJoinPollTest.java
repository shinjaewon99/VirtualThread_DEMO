package com.demo.virtualThread.pureJava;

import java.util.List;
import java.util.Optional;

public class ForkJoinPollTest {
    public static void main(String[] args) {
        // 단일 쓰레드에서 동작
//        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//
//        Optional<Integer> op = list.stream().filter(integer -> {
//            boolean b = integer % 2 == 0;
//            return b;
//        }).findAny();
//
//        System.out.println(op.get());

        // 다중 쓰레드에서 바라보는 데이터가 다르므로, 2가 출력될수도 4가 출력될수도 있음
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // 쓰레드 풀이 필요하다고 생각할때 Java가 ForkJoinPoll 을 통해 쓰레드를 할당
        Optional<Integer> op = list.parallelStream()
                .filter(integer -> {
                    System.out.println("i : " + integer + ", thread : " + Thread.currentThread() + " , daemon: " + Thread.currentThread().isDaemon());
                    boolean b = integer % 2 == 0;
                    return b;
                }).findAny();

        System.out.println(op.get());
    }
}
