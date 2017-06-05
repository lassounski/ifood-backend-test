package com.ifood.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ifood.models.CityWeather;
import com.ifood.models.ImmutableCityWeather;
import com.ifood.models.Weather;
import com.ifood.models.WeatherDescription;
import com.ifood.services.FallbackCityWeatherService;
import com.ifood.services.WeatherCheckService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RunWith(SpringRunner.class)
@WebMvcTest(WeatherCheckController.class)
public class WeatherCheckServiceControllerTest {

    final String CAMPINAS = "campinas";
    final String CITY = "city";
    final String WEATHER = "/weather";
    final String EXPECTED_GRACEFUL_FAIL_MESSAGE = "Unfortunately the service that you are looking for is down.";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherCheckService weatherCheckService;

    @MockBean
    private FallbackCityWeatherService fallbackCityWeatherService;

    @Before
    public void setUp() {
        given(fallbackCityWeatherService.getWeather(CAMPINAS)).
                willReturn(Optional.of(getCityWeather()));
    }

    @Test
    public void shouldFetchCityWeather() throws Exception {
        given(weatherCheckService.getCityWeatherData(any()))
                .willReturn(CompletableFuture.completedFuture(getCityWeather()));

        MvcResult result = mockMvc.perform(get(WEATHER).param(CITY, CAMPINAS))
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Campinas"))
                .andExpect(jsonPath("main.temp").value(295.15));
    }

    @Test
    public void shouldFetchCityWeatherFromFallbackCache() throws Exception {
        given(weatherCheckService.getCityWeatherData(any()))
                .willReturn(CompletableFuture.supplyAsync(() -> {
                    throwException();
                    return getCityWeather();
                }));


        MvcResult result = mockMvc.perform(get(WEATHER).param(CITY, CAMPINAS))
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Campinas"))
                .andExpect(jsonPath("main.temp").value(295.15));
    }

    @Test
    public void shouldGracefullyFail() throws Exception {
        given(weatherCheckService.getCityWeatherData(any()))
                .willReturn(CompletableFuture.supplyAsync(() -> {
                    throwException();
                    return getCityWeather();
                }));

        given(fallbackCityWeatherService.getWeather(CAMPINAS)).
                willReturn(Optional.empty());

        MvcResult result = mockMvc.perform(get(WEATHER).param(CITY, CAMPINAS))
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString(EXPECTED_GRACEFUL_FAIL_MESSAGE)));
    }

    private void throwException() {
        throw new RuntimeException();
    }

    public CityWeather getCityWeather() {
        WeatherDescription weatherDescription = WeatherDescription.builder()
                .withStatus("Clear")
                .withDescription("clear sky")
                .build();
        Weather weather = Weather.builder()
                .withTemparature(295.15)
                .withPressure(1021)
                .withHumidity(64)
                .build();
        return ImmutableCityWeather.builder()
                .addWeatherDescription(weatherDescription)
                .withCityName("Campinas")
                .withWeather(weather)
                .build();
    }
}
