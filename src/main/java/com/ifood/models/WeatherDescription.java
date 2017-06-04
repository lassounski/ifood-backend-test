package com.ifood.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableWeatherDescription.class)
@JsonDeserialize(as = ImmutableWeatherDescription.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Value.Style(init = "with*")
public abstract class WeatherDescription {

    public abstract String getDescription();

    @JsonProperty("main")
    public abstract String getStatus();

    public static ImmutableWeatherDescription.Builder builder(){
        return ImmutableWeatherDescription.builder();
    }
}
