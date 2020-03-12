package com.backbase.message;

import com.backbase.buildingblocks.presentation.errors.BadRequestException;
import com.backbase.buildingblocks.presentation.errors.InternalServerErrorException;
import com.backbase.dbs.messaging_service.api.service.v2.MessageApi;
import com.backbase.dbs.messaging_service.api.service.v2.model.Message;
import com.backbase.dbs.messaging_service.api.service.v2.model.MessagePostResponseBody;
import com.backbase.message.domain.Greeting;
import com.backbase.message.mapper.GreetingsMapper;
import com.backbase.message.service.GreetingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/v1")
@RestController
public class ExampleController implements MessageApi {

    @Autowired
    private GreetingsService greetingsService;


    @Override
    public ResponseEntity<Message> getMessage(@NotNull @Valid String id) throws BadRequestException,
            InternalServerErrorException {
        Greeting greeting = greetingsService.getGreetingById(id);
        return new ResponseEntity<>(GreetingsMapper.INSTANCE.greetingToMessage(greeting), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MessagePostResponseBody> postMessage(@Valid Message message) throws BadRequestException,
            InternalServerErrorException {
        Greeting greeting = GreetingsMapper.INSTANCE.messageToGreeting(message);
        greetingsService.saveGreeting(greeting);
        return new ResponseEntity<>(new MessagePostResponseBody().id(greeting.getId()), HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<Void> putMessage(@Valid Message messageResponse) throws BadRequestException,
            InternalServerErrorException{
        Greeting greeting = greetingsService.getGreetingById(messageResponse.getId());
        greeting.setMessage(messageResponse.getMessage());
        greetingsService.saveGreeting(greeting);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteMessage(@NotNull @Valid String id) throws BadRequestException,
            InternalServerErrorException{
        Greeting greeting = greetingsService.getGreetingById(id);
        greetingsService.deleteGreeting(greeting);
        return ResponseEntity.noContent().build();
    }


    @Override
    public ResponseEntity<List<Message>> getMessages() throws BadRequestException, InternalServerErrorException {
        List<Greeting> greetings = greetingsService.getGreetings();
        return new ResponseEntity<>(GreetingsMapper.INSTANCE.greetingsToMessages(greetings), HttpStatus.OK);
    }

}