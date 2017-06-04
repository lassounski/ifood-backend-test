package com.ifood.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableCityWeather.class)
@JsonDeserialize(as = ImmutableCityWeather.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Value.Style(init = "with*")
public abstract class CityWeather {

    @JsonProperty("name")
    public abstract String getCityName();

    @JsonProperty("weather")
    public abstract List<WeatherDescription> getWeatherDescription();

    @JsonProperty("main")
    public abstract Weather getWeather();

    public static ImmutableCityWeather.Builder builder() {
        return CityWeather.builder();
    }
}
