package com.ifood.configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration {

    public static final String CITY_WEATHER = "cityWeather";
    public static final String FALLBACK_CITY_WEATHER = "fallbackCityWeather";

    @Value("${com.ifood.cache.initialCapacity}")
    private int initialCapacity;

    @Value("${com.ifood.cache.maximumSize}")
    private int maximumSize;

    @Value("${com.ifood.cache.expireTtlSeconds}")
    private long expireTtlSeconds;

    @Value("${com.ifood.fallback.cache.initialCapacity}")
    private int initialFallbackCapacity;

    @Value("${com.ifood.fallback.cache.maximumSize}")
    private int maximumFallbackSize;

    @Value("${com.ifood.fallback.cache.expireTtlSeconds}")
    private long expireFallbackTtlSeconds;

    @Bean(CITY_WEATHER)
    public CaffeineCache cityWeatherCache() {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .expireAfterWrite(expireTtlSeconds, TimeUnit.SECONDS)
                .build();
        return new CaffeineCache(CITY_WEATHER, cache);
    }

    @Bean(FALLBACK_CITY_WEATHER)
    public CaffeineCache cityFallbackWeatherCache() {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .initialCapacity(initialFallbackCapacity)
                .maximumSize(maximumFallbackSize)
                .expireAfterAccess(expireFallbackTtlSeconds, TimeUnit.SECONDS)
                .build();
        return new CaffeineCache(FALLBACK_CITY_WEATHER, cache);
    }
}
