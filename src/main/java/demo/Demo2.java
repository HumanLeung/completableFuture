package demo;

import java.util.concurrent.*;

public class Demo2 {
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
        },executor).whenCompleteAsync((res, exec) -> {
            System.out.println("whenCompleteAsync SubThread is executed..."+Thread.currentThread().getName());
            System.out.println("res = "+ res);
            System.out.println("exec = "+ exec);
        }).exceptionally((e) -> {
            System.out.println("e1 = " + e);
            return null;
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task2 SubTask is executed..." + Thread.currentThread().getName());
            int i = 10 / 0;
            return 10;
        },executor).whenCompleteAsync((res, exec) -> {
            System.out.println("2whenCompleteAsync sub Thread is executed..."+ Thread.currentThread().getName());
            System.out.println("res = " + res);
            System.out.println("exec = " + exec);
        },executor).exceptionally((e) -> {
            System.out.println("e2 = " + e);
            return 100;
        });
        System.out.println("Main Thread end..."+future2.get());
        System.out.println(future1);
    }
}
