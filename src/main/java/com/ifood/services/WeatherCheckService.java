package com.ifood.services;

import static com.ifood.configuration.CacheConfiguration.CITY_WEATHER;

import com.ifood.models.CityWeather;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class WeatherCheckService {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherCheckService.class);

    private final AsyncHttpClient httpClient;
    private final AsyncCompletionHandler asyncCompletionHandler;

    private final String OPEN_WEATHER_QUERY_URL = "http://api.openweathermap" +
            ".org/data/2.5/weather?q=%s&APPID=%s";

    @Value("${com.ifood.open.weather.token}")
    private String openWeatherAPIkey;

    @Autowired
    public WeatherCheckService(AsyncHttpClient httpClient, AsyncCompletionHandler asyncCompletionHandler) {
        this.httpClient = httpClient;
        this.asyncCompletionHandler = asyncCompletionHandler;
    }

    @Cacheable(CITY_WEATHER)
    public CompletableFuture<CityWeather> getCityWeatherData(String cityName) {
        LOG.info("Querying Open Weather API for [{}]", cityName);

        return httpClient.prepareGet(String.format(OPEN_WEATHER_QUERY_URL, cityName, openWeatherAPIkey))
                .execute(asyncCompletionHandler)
                .toCompletableFuture();
    }
}
