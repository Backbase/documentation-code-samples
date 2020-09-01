package com.backbase.banking;

import com.backbase.buildingblocks.backend.communication.http.HttpCommunicationConfiguration;
import com.backbase.messaging.api.client.ApiClient;
import com.backbase.messaging.api.client.v2.MessageApi;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import javax.validation.constraints.Pattern;

// tag::MessagingServiceRestClientConfiguration[]
@Configuration
@ConfigurationProperties("backbase.communication.services.name")
public class MessagingServiceRestClientConfiguration {

    private String serviceId = "messaging-service";
    private String serviceUrl = "/service-api/v2";
    public static final String INTERCEPTORS_ENABLED_HEADER = "INTERCEPTORS_ENABLED_HEADER";

    @Value("${backbase.communication.http.default-scheme:http}")
    @Pattern(regexp = "https?")
    private String scheme;

    /**
     * Creates a REST client.
     *
     * @param restTemplate the RestTemplate for the client.
     * @return the client.
     */
    @Bean
    public MessageApi createGeneratedClassApiClient(
            @Qualifier(HttpCommunicationConfiguration.INTER_SERVICE_REST_TEMPLATE_BEAN_NAME) RestTemplate restTemplate) {
        ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setBasePath(scheme + "://" + serviceId + serviceUrl);
        apiClient.addDefaultHeader(HttpCommunicationConfiguration.INTERCEPTORS_ENABLED_HEADER,
                Boolean.TRUE.toString());
        return new MessageApi(apiClient);
    }
}
// end::MessagingServiceRestClientConfiguration[]
