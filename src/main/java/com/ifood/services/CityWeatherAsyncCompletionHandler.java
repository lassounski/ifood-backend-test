package com.ifood.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifood.models.CityWeather;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CityWeatherAsyncCompletionHandler extends AsyncCompletionHandler<CityWeather> {

    private static final Logger LOG = LoggerFactory.getLogger(CityWeatherAsyncCompletionHandler.class);

    @Autowired
    private FallbackCityWeatherService fallbackCityWeatherService;

    @Override
    public CityWeather onCompleted(Response response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CityWeather cityWeather = objectMapper.readValue(response.getResponseBody(), CityWeather.class);
        fallbackCityWeatherService.setCityWeather(cityWeather.getCityName(), cityWeather);

        return cityWeather;
    }

    @Override
    public void onThrowable(Throwable t) {
        LOG.error("There was a problem processing the request to OpenWeather", t);
    }
}
