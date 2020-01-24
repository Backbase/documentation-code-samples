package com.backbase.example.mapper;

import com.backbase.example.domain.Greeting;
import com.backbase.service.example.rest.spec.v1.message.MessageGetResponseBody;
import com.backbase.service.example.rest.spec.v1.message.MessagePostRequestBody;
import com.backbase.service.example.rest.spec.v1.message.MessagesGetResponseBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GreetingsMapper {

    GreetingsMapper INSTANCE = Mappers.getMapper( GreetingsMapper.class);

    MessageGetResponseBody greetingToMessage(Greeting greeting);
    List<MessagesGetResponseBody> greetingsToMessages(List<Greeting> greetings);
    Greeting messageToGreeting(MessagePostRequestBody message);
}
