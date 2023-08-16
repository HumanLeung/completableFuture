package demo2;/*
 *@program:completable-future-master
 *@author: liangxm
 *@Time: 2023/8/15  16:50
 *@description:
 */

import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * @author 梁晓明
 */
public class Practice01 {

   private static final Logger logger = Logger.getLogger(Practice01.class.getName());

    static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            5,
            50,
            10,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws ExecutionException,
            InterruptedException {
        CompletableFuture<Void> asyncFuture = CompletableFuture.runAsync(() ->
                logger.info("run async method is running, thread name is " +
                        Thread.currentThread().getName()),executor)
                .thenRunAsync(() -> logger.info("this is thenRunAsync method"));

        logger.info(asyncFuture.get()+" this is async method");

       CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
           logger.info("supplyAsync method is running, thread name is " +
                   Thread.currentThread().getName());
           return "Done!";
        },executor);
       logger.info(supplyAsync.get()+" this is supplyAsync method");

       logger.info("Main method is running");
    }
}
