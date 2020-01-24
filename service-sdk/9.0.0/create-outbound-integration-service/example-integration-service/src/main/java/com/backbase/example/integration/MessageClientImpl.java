package com.backbase.example.integration;

import com.backbase.example.model.Message;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageClientImpl implements MessageClient {

    private static final String SERVICE_URL = "http://echo.jsontest.com/messageId/abcdef-393939-dffdfdf/message/Hello%20World/";
    private RestTemplate restTemplate;

    public MessageClientImpl(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }
// tag::getMessages[]
    @Override
    public List<Message> getMessages() throws IOException {
        /** NOTE: This integration could be completed by creating a code generated client e.g. off a Open API spec */
        String responseJSON = restTemplate.exchange(
                URI.create(SERVICE_URL),
                HttpMethod.GET, null, String.class).getBody();

        Message message = new ObjectMapper().readValue(responseJSON,
                new TypeReference<Message>() {});

        List<Message> messages = new ArrayList<>();
        messages.add(message);
        return messages;
    }
// end::getMessages[]   
}
