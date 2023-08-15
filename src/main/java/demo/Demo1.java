package demo;

import java.util.concurrent.*;

/**
 * @author Administrator
 */
public class Demo1 {

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
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("sub thread..."+ Thread.currentThread().getName());
        },executor);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task2 SubTask is executed..." + Thread.currentThread().getName());
           return 10;
        },executor);
        System.out.println("Main Thread end..."+future2.get());
        System.out.println(future1);
    }
}
