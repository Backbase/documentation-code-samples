package com.backbase.message.api;

import com.backbase.dbs.messaging_service.api.service.v2.MessageApi;
import com.backbase.dbs.messaging_service.api.service.v2.model.MessagePostResponseBody;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ExampleController implements MessageApi {


    @Override
    public ResponseEntity<Void> deleteMessage(@NotNull @Valid String id) {
        //TODO: Implement method with proper logic
        return null;
    }

    @Override
    public ResponseEntity<com.backbase.dbs.messaging_service.api.service.v2.model.Message> getMessage(@NotNull @Valid String id) {
        //TODO: Implement method with proper logic
        return null;
    }

    @Override
    public ResponseEntity<List<com.backbase.dbs.messaging_service.api.service.v2.model.Message>> getMessages() {
        //TODO: Implement method with proper logic
        return null;
    }

    @Override
    public ResponseEntity<MessagePostResponseBody> postMessage(com.backbase.dbs.messaging_service.api.service.v2.model.@Valid Message message) {
        //TODO: Implement method with proper logic
        return null;
    }

    @Override
    public ResponseEntity<Void> putMessage(com.backbase.dbs.messaging_service.api.service.v2.model.@Valid Message message) {
        //TODO: Implement method with proper logic
        return null;
    }
}