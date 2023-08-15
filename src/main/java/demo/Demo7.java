package demo;

import java.util.concurrent.*;

/**
 * @author Administrator
 */
public class Demo7 {
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
            50,
            10,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Main Thread start...");
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("task1 sub thread is executed..." +Thread.currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("task1 sub thread is end." +Thread.currentThread().getName());
            return 20;
        },executor);

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("task2 sub thread is executed end..."+Thread.currentThread().getName());
            int i = 10 / 0;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 10;
        },executor);

       CompletableFuture<Integer> future3 = future1.thenCombineAsync(future2,(f1,f2) -> {
            System.out.println("f1 = " + f1);
            System.out.println("f2 = " + f2);
            return f1 + f2;
        },executor);

        System.out.println("main thread end..."+future3.get());
    }
}
