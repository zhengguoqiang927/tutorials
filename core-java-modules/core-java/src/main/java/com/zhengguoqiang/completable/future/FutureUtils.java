package com.zhengguoqiang.completable.future;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class FutureUtils {

    /**
     * 将List<CompletableFuture<T>> 转为 CompletableFuture<List<T>>
     */
    public static <T> CompletableFuture<List<T>> sequence(Collection<CompletableFuture<T>> completableFutures) {
        return CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture<?>[0]))
                .thenApply(v -> completableFutures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
    }

    /**
     * 将List<CompletableFuture<List<T>>> 转为 CompletableFuture<List<T>>
     * 多用于分页查询的场景
     */
    public static <T> CompletableFuture<List<T>> sequenceList(Collection<CompletableFuture<List<T>>> completableFutures){
        return CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture<?>[0]))
                .thenApply(v -> completableFutures.stream()
                .flatMap(future -> future.join().stream())
                        .collect(Collectors.toList()));

    }

    /*
     * 将List<CompletableFuture<Map<K, V>>> 转为 CompletableFuture<Map<K, V>>
     * @Param mergeFunction 自定义key冲突时的merge策略
     */
    public static <K,V> CompletableFuture<Map<K,V>> sequenceMap(Collection<CompletableFuture<Map<K,V>>> completableFutures,
                                                                BinaryOperator<V> mergeFunction){
        return CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture<?>[0]))
                .thenApply(v -> completableFutures.stream()
                .map(CompletableFuture::join)
                .flatMap(kvMap -> kvMap.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,mergeFunction)));
    }

    public static void main(String[] args) {
        CompletableFuture<List<Integer>> future1 = CompletableFuture.supplyAsync(() -> {
            List<Integer> list = new ArrayList<>();
            list.add(11);
            list.add(12);
            list.add(13);
            return list;
        });
        CompletableFuture<List<Integer>> future2 = CompletableFuture.supplyAsync(() -> {
            List<Integer> list = new ArrayList<>();
            list.add(21);
            list.add(22);
            list.add(23);
            return list;
        });
        CompletableFuture<List<Integer>> future3 = CompletableFuture.supplyAsync(() -> {
            List<Integer> list = new ArrayList<>();
            list.add(31);
            list.add(32);
            list.add(33);
            return list;
        });
        List<CompletableFuture<List<Integer>>> completableFutures = Arrays.asList(future1, future2, future3);
        CompletableFuture<List<Integer>> future = sequenceList(completableFutures);
        future.whenComplete((list,throwable) -> {
            if (throwable != null)
                System.out.println(throwable.getCause().getMessage());
            list.forEach(System.out::println);
        });
    }
}
