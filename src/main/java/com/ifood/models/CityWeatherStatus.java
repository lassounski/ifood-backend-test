package com.ifood.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CityWeatherStatus {
    OK("OK"), DOWN("Open Weather API down. Returning last valid result.");

    private String description;

    CityWeatherStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static CityWeatherStatus fromName(String name) {
        return CityWeatherStatus.valueOf(name);
    }
}
