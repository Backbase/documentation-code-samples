package com.backbase.example.mapper;

import com.backbase.example.domain.Greeting;
import com.backbase.service.example.rest.spec.v1.model.Message;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GreetingsMapper {

    GreetingsMapper INSTANCE = Mappers.getMapper( GreetingsMapper.class);

    Message greetingToMessage(Greeting greeting);
    List<Message> greetingsToMessages(List<Greeting> greetings);
    Greeting messageToGreeting(Message message);
}
