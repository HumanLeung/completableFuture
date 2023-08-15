package demo;

import java.util.concurrent.*;

public class Demo4 {

    static ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
            50,
            10,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Main Thread start...");
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            System.out.println("sub thread..."+ Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },executor).thenRunAsync(() -> {
            System.out.println("task2 sub thread is executed..." + Thread.currentThread().getName());
        },executor);

        CompletableFuture.supplyAsync(() -> {
            System.out.println("sub3 thread..."+ Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 100;
        },executor).thenAcceptAsync((res) -> {
            System.out.println("task4 sub thread is executed..."+Thread.currentThread().getName()+" : "+res);
        },executor);

       CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("sub5 thread..."+ Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 100;
        },executor).thenApplyAsync((res) -> {
            System.out.println("task6 sub thread is executed..."+Thread.currentThread().getName()+" : "+res);
            return res * 100;
            },executor);
        System.out.println("Main Thread end..." + future3.get());
        System.out.println(future1);
    }
}
