package com.zhengguoqiang;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        LoadingCache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(256L)
                .initialCapacity(1)
                .expireAfterAccess(2, TimeUnit.DAYS)
                .expireAfterWrite(2, TimeUnit.HOURS)
                .recordStats()
                .build(new CacheLoader<String, String>() {
                    @Override
                    public @Nullable String load(String key) throws Exception {
                        return "value_" + key;
                    }

                    @Override
                    public @Nullable String reload(String key, String oldValue) throws Exception {
                        System.out.println("oldvalue:" + oldValue);
                        return "value_" + key;
                    }
                });
        cache.put("12","13");
        cache.get("12");
    }
}
