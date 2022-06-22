package com.zhengguoqiang.completable.future;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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
}
