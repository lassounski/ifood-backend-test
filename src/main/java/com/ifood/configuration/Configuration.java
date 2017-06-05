package com.ifood.configuration;

import com.ifood.services.CityWeatherAsyncCompletionHandler;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public AsyncHttpClient getAsyncHttpClient() {
        AsyncHttpClientConfig config = new DefaultAsyncHttpClientConfig.Builder()
                .setRequestTimeout(10000).build();
        return new DefaultAsyncHttpClient(config);
    }

    @Bean
    public AsyncCompletionHandler getAsyncCompletionHandler() {
        return new CityWeatherAsyncCompletionHandler();
    }
}
