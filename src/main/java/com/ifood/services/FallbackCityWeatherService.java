package com.ifood.services;

import com.ifood.configuration.CacheConfiguration;
import com.ifood.models.CityWeather;
import com.ifood.models.CityWeatherStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FallbackCityWeatherService {

    @Autowired
    @Qualifier(CacheConfiguration.FALLBACK_CITY_WEATHER)
    private CaffeineCache caffeineCache;

    public Optional<CityWeather> getWeather(String city) {
        return Optional.ofNullable(
                caffeineCache.get(city.toLowerCase(), CityWeather.class)
        );
    }

    public void setCityWeather(String city, CityWeather cityWeather) {
        CityWeather cityWeather1 = CityWeather.builder()
                .from(cityWeather)
                .withServiceStatus(CityWeatherStatus.DOWN)
                .build();
        caffeineCache.put(city.toLowerCase(), cityWeather1);
    }
}
