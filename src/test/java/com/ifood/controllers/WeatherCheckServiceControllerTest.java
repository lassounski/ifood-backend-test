package com.ifood.controllers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ifood.models.CityWeather;
import com.ifood.models.ImmutableCityWeather;
import com.ifood.models.Weather;
import com.ifood.models.WeatherDescription;
import com.ifood.services.WeatherCheckService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.concurrent.CompletableFuture;

@RunWith(SpringRunner.class)
@WebMvcTest(WeatherCheckController.class)
public class WeatherCheckServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherCheckService weatherCheckService;

    @Test
    public void shouldFetchCityWeather() throws Exception {
        given(weatherCheckService.getCityWeatherData(any()))
                .willReturn(CompletableFuture.completedFuture(getCityWeather()));

        MvcResult result = mockMvc.perform(get("/weather").param("city", "campinas"))
                .andDo(print())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Campinas"));
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
