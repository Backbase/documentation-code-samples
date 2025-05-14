package com.backbase.example.service;

import com.backbase.buildingblocks.communication.client.ApiClientConfig;
import com.backbase.buildingblocks.communication.http.HttpCommunicationConfiguration;
import com.backbase.integration.example.client.ApiClient;
import com.backbase.integration.example.client.v1.MessageApi;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties("backbase.communication.services.messaging-integration-service")
@Validated
public class IntegrationApiClientConfig extends ApiClientConfig {

    public static final String SERVICE_ID = "messaging-integration-service";

    public IntegrationApiClientConfig() {
        super(SERVICE_ID);
    }

    /**
     * Creates a REST client.
     *
     * @return the client.
     */

    @Bean
    public MessageApi messageIntegrationApiClient() {
        return new MessageApi(createaApiClient());
    }

    private ApiClient createaApiClient() {
        return new ApiClient(getRestTemplate())
            .setBasePath(createBasePath())
            .addDefaultHeader(HttpCommunicationConfiguration.INTERCEPTORS_ENABLED_HEADER, Boolean.TRUE.toString());
    }
}