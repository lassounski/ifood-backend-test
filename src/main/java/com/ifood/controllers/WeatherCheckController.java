package com.ifood.controllers;

import com.ifood.models.CityWeather;
import com.ifood.services.WeatherCheckService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class WeatherCheckController {

    @Autowired
    private WeatherCheckService weatherCheckService;

    @RequestMapping("/weather")
    public DeferredResult<ResponseEntity<CityWeather>> getWeather(@RequestParam("city") String city) {
        DeferredResult<ResponseEntity<CityWeather>> deferredResult = new DeferredResult<>();

        weatherCheckService.getCityWeatherData(city)
                .exceptionally(throwable -> {
                    deferredResult.setResult(new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE));
                    return null;
                })
                .thenAccept(cityWeather -> deferredResult.setResult(new ResponseEntity<>(cityWeather, HttpStatus.OK)));

        return deferredResult;
    }
}
