package com.backbase.example.service;

import com.backbase.buildingblocks.communication.client.ApiClientConfig;
import com.backbase.integration.example.client.ApiClient;
import com.backbase.integration.example.client.v1.MessageApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntegrationApiClientConfig extends ApiClientConfig {

    public IntegrationApiClientConfig() {
        super("example-integration-service");
    }

    @Bean
    public MessageApi exampleIntegrationApiClient() {
        return new MessageApi(createaApiClient());
    }

    private ApiClient createaApiClient() {
        return new ApiClient(getRestTemplate())
            .setBasePath(createBasePath());
    }
}