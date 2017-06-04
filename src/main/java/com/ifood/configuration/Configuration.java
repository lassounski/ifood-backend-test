package com.ifood.configuration;

import com.ifood.services.CityWeatherAsyncCompletionHandler;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public AsyncHttpClient getAsyncHttpClient(){
        return new DefaultAsyncHttpClient();
    }

    @Bean
    public AsyncCompletionHandler getAsyncCompletionHandler(){
        return new CityWeatherAsyncCompletionHandler();
    }
}
