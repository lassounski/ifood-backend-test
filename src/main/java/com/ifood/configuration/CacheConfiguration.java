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

    @Value("${com.catapult.messaging.messageDlrCache.initialCapacity:100}")
    private int initialCapacity;

    @Value("${com.catapult.messaging.messageDlrCache.maximumSize:1000}")
    private int maximumSize;

    @Value("${com.catapult.messaging.messageDlrCache.expireTtlSeconds:10}")
    private long expireTtlSeconds;

    @Bean(CITY_WEATHER)
    public CaffeineCache cityWeatherCache() {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .expireAfterWrite(expireTtlSeconds, TimeUnit.SECONDS)
                .build();
        return new CaffeineCache(CITY_WEATHER, cache);
    }
}
