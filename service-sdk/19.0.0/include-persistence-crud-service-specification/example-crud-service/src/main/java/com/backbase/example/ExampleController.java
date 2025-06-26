package com.backbase.example;

import com.backbase.example.domain.Greeting;
import com.backbase.example.mapper.GreetingsMapper;
import com.backbase.example.service.GreetingsService;
import com.backbase.service.example.rest.spec.v1.MessageApi;
import com.backbase.service.example.rest.spec.v1.model.Message;
import com.backbase.service.example.rest.spec.v1.model.MessagePostResponseBody;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController implements MessageApi {

    @Autowired
    private GreetingsService greetingsService;

    @Override
    public ResponseEntity<Void> deleteMessage(String id) {
        Greeting greeting = greetingsService.getGreetingById(id);
        greetingsService.deleteGreeting(greeting);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Message> getMessage(String id) {
        Greeting greeting = greetingsService.getGreetingById(id);

        return ResponseEntity.ok(GreetingsMapper.INSTANCE.greetingToMessage(greeting));
    }

    @Override
    public ResponseEntity<List<Message>> getMessages() {
        List<Greeting> greetings = greetingsService.getGreetings();

        return ResponseEntity.ok(GreetingsMapper.INSTANCE.greetingsToMessages(greetings));
    }

    @Override
    public ResponseEntity<MessagePostResponseBody> postMessage(Message message) {
        Greeting greeting = GreetingsMapper.INSTANCE.messageToGreeting(message);
        greetingsService.saveGreeting(greeting);

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessagePostResponseBody().id(greeting.getId()));
    }

    @Override
    public ResponseEntity<Void> putMessage(Message message) {
        Greeting greeting = greetingsService.getGreetingById(message.getId());
        greeting.setMessage(message.getMessage());
        greetingsService.saveGreeting(greeting);

        return ResponseEntity.noContent().build();
    }
}