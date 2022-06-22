package com.zhengguoqiang.completable.future;

import java.util.concurrent.*;

public class CompletableFutureTest {

    /**
     * 代码执行在哪个线程上？
     *
     * CompletableFuture实现了CompletionStage接口，通过丰富的回调方法，支持各种组合操作，每种组合场景都有同步和异步两种方法。
     *
     * 同步方法（即不带Async后缀的方法）有两种情况。
     *
     * 如果注册时被依赖的操作已经执行完成，则直接由当前线程执行。
     * 如果注册时被依赖的操作还未执行完，则由回调线程执行。
     *
     * 异步方法（即带Async后缀的方法）：可以选择是否传递线程池参数Executor运行在指定线程池中；当不传递Executor时，会使用ForkJoinPool中的共用线程池CommonPool（CommonPool的大小是CPU核数-1，如果是IO密集的应用，线程数可能成为瓶颈）。
     */
    private void executeThread(){
        ExecutorService executor = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync 执行线程：" + Thread.currentThread().getName());
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "";
        }, executor);

        //此时，如果future1中的业务操作已经执行完毕并返回，则该thenApply直接由当前main线程执行；否则，将会由执行以上业务操作的threadPool1中的线程执行。
        CompletableFuture<String> future2 = future1.thenApply(value -> {
            System.out.println("thenApply 执行线程：" + Thread.currentThread().getName());
            return value + "1";
        });

        //使用ForkJoinPool中的共用线程池CommonPool
        future1.thenApplyAsync(value -> {
            System.out.println("thenApply 异步执行线程(common pool)：" + Thread.currentThread().getName());
            return value + "1";
        });

        future1.thenApplyAsync(value -> {
            System.out.println("thenApply 异步执行线程(executor)：" + Thread.currentThread().getName());
            return value + "1";
        },executor);

    }

    public static void main(String[] args) {
        CompletableFutureTest test = new CompletableFutureTest();
        test.executeThread();
    }
}
