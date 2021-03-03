package com.backbase.example;

import com.backbase.buildingblocks.presentation.errors.InternalServerErrorException;
import com.backbase.example.api.client.v1.MessageApi;
import com.backbase.example.api.client.v1.model.MessagePostResponseBody;
import com.backbase.example.integration.MessageClient;
import com.backbase.example.mapper.IntegrationMessageMapper;
import com.backbase.example.model.Message;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController implements MessageApi {

    @Autowired
    private MessageClient messageClient;

    @Override
    public ResponseEntity<Void> deleteMessage(@Valid @NotNull String id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }


    @Override
    public ResponseEntity<com.backbase.example.api.client.v1.model.Message> getMessage(@Valid @NotNull String id) {

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    // tag::getIntegration[]
    @Override
    public ResponseEntity<List<com.backbase.example.api.client.v1.model.Message>> getMessages() {
        List<Message> messages = null;

        try {
            messages = messageClient.getMessages();
        } catch (IOException e) {
            throw new InternalServerErrorException("Error has occurred sending message");
        }

        return ResponseEntity.ok(IntegrationMessageMapper.INSTANCE.messagesToIntegrationGetResponseBodys(messages));
    }
    // end::getIntegration[]

    @Override
    public ResponseEntity<MessagePostResponseBody> postMessage(
        com.backbase.example.api.client.v1.model.@Valid Message message) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<Void> putMessage(com.backbase.example.api.client.v1.model.@Valid Message message) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

}
