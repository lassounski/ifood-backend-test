package com.ifood.controllers;

import com.ifood.models.CityWeather;
import com.ifood.services.FallbackCityWeatherService;
import com.ifood.services.WeatherCheckService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Optional;

@RestController
public class WeatherCheckController {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherCheckController.class);

    @Autowired
    private WeatherCheckService weatherCheckService;

    @Autowired
    private FallbackCityWeatherService fallbackCityWeatherService;

    @RequestMapping("/weather")
    public DeferredResult<ResponseEntity<?>> getWeather(@RequestParam("city") String city) {
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();

        weatherCheckService.getCityWeatherData(city)
                .handle((cityWeather, throwable) -> {
                    if (cityWeather != null) {
                        deferredResult.setResult(new ResponseEntity<>(cityWeather, HttpStatus.OK));
                    } else {
                        LOG.error("Loading fallback response for city [{}]", city);
                        Optional<CityWeather> possibleCityWeather = fallbackCityWeatherService.getWeather(city);
                        if (possibleCityWeather.isPresent()) {
                            deferredResult.setResult(
                                    new ResponseEntity<>(possibleCityWeather.get(), HttpStatus.OK));
                        } else {
                            LOG.error("No fallback response for city [{}].", city);
                            deferredResult.setResult(
                                    new ResponseEntity<>("503 -" +
                                            " Unfortunately the service that you are looking for is down." +
                                            " We are doing our best to provide a solution.", HttpStatus.SERVICE_UNAVAILABLE));
                        }
                    }
                    return null;
                });
        return deferredResult;
    }
}
