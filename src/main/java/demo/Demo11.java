package demo;

import java.util.concurrent.*;

public class Demo11 {
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
            50,
            10,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Main Thread start...");
        CompletableFuture<Object> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("task1 sub thread is executed..." +Thread.currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("task1 sub thread is end." +Thread.currentThread().getName());
            return 100;
        },executor);

        CompletableFuture<Object> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("task2 sub thread is executed end..."+Thread.currentThread().getName());
            int i = 10 / 0;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 10;
        },executor);

        CompletableFuture<Object> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("task3 sub thread is executed end..."+Thread.currentThread().getName());
            int i = 10 / 0;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 10;
        },executor);

        CompletableFuture<Void> f = CompletableFuture.allOf(future1, future2, future3);
        f.get();
        System.out.println("Main Thread end..."+future1.get()+"_"+future2.get()+"_"+future3.get());
    }
}
