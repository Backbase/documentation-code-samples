package com.backbase.example;

import com.backbase.buildingblocks.presentation.errors.BadRequestException;
import com.backbase.buildingblocks.presentation.errors.InternalServerErrorException;
import com.backbase.example.domain.Greeting;
import com.backbase.example.mapper.GreetingsMapper;
import com.backbase.example.service.GreetingsService;
import com.backbase.service.example.rest.spec.v1.message.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
public class ExampleController implements MessageApi {

    @Autowired
    private GreetingsService greetingsService;


    @Override
    public MessageGetResponseBody getMessage(@RequestParam(value = "id") String id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws BadRequestException, InternalServerErrorException {
        Greeting greeting = greetingsService.getGreetingById(id);
        return GreetingsMapper.INSTANCE.greetingToMessage(greeting);
    }

    @Override
    public MessagePostResponseBody postMessage(@Valid MessagePostRequestBody messagePostRequestBody, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws BadRequestException, InternalServerErrorException {
        Greeting greeting = GreetingsMapper.INSTANCE.messageToGreeting(messagePostRequestBody);
        greetingsService.saveGreeting(greeting);
        return new MessagePostResponseBody().withId(greeting.getId());
    }

    @Override
    public void putMessage(@Valid MessagePutRequestBody messagePutRequestBody, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws BadRequestException, InternalServerErrorException {
        Greeting greeting = greetingsService.getGreetingById(messagePutRequestBody.getId());
        greeting.setMessage(messagePutRequestBody.getMessage());
        greetingsService.saveGreeting(greeting);
    }

    @Override
    public void deleteMessage(String id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws BadRequestException, InternalServerErrorException {
        Greeting greeting = greetingsService.getGreetingById(id);
        greetingsService.deleteGreeting(greeting);
    }

    @Override
    public List<MessagesGetResponseBody> getMessages(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws BadRequestException, InternalServerErrorException {
        List<Greeting> greetings = greetingsService.getGreetings();
        return GreetingsMapper.INSTANCE.greetingsToMessages(greetings);
    }
}