package com.backbase.banking.config;

import com.backbase.buildingblocks.communication.client.ApiClientConfig;
import com.backbase.buildingblocks.communication.http.HttpCommunicationConfiguration;
import com.backbase.messaging.api.client.ApiClient;
import com.backbase.messaging.api.client.v2.MessageApi;
import org.springframework.context.annotation.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

// tag::MessagingServiceRestClientConfiguration[]
@Configuration
@ConfigurationProperties("backbase.communication.services.name")
@Validated
public class MessagingServiceRestClientConfiguration extends ApiClientConfig {

    public static final String MESSAGING_SERVICE_ID = "messaging-service";

    public MessagingServiceRestClientConfiguration() {
        super(MESSAGING_SERVICE_ID);
    }

    /**
     * Creates a REST client.
     *
     * @return the client.
     */
    @Bean
    public MessageApi createGeneratedClassApiClient() {
        return new MessageApi(createApiClient());
    }

    private ApiClient createApiClient() {
        return new ApiClient(getRestTemplate())
            .setBasePath(createBasePath())
            .addDefaultHeader(HttpCommunicationConfiguration.INTERCEPTORS_ENABLED_HEADER, Boolean.TRUE.toString());
    }
}
// end::MessagingServiceRestClientConfiguration[]
