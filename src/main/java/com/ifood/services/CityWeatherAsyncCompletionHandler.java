package com.ifood.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifood.models.CityWeather;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CityWeatherAsyncCompletionHandler extends AsyncCompletionHandler<CityWeather> {

    private static final Logger LOG = LoggerFactory.getLogger(CityWeatherAsyncCompletionHandler.class);

    @Override
    public CityWeather onCompleted(Response response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.getResponseBody(), CityWeather.class);
    }

    @Override
    public void onThrowable(Throwable t) {
        LOG.error("There was a problem processing the request to OpenWeather", t);
    }
}
