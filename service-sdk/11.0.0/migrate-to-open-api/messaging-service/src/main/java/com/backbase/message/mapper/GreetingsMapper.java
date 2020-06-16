package com.backbase.message.mapper;

import com.backbase.dbs.messaging_service.api.service.v2.model.Message;
import com.backbase.message.domain.Greeting;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GreetingsMapper {

    GreetingsMapper INSTANCE = Mappers.getMapper(GreetingsMapper.class);

    Message greetingToMessage(Greeting greeting);

    List<Message> greetingsToMessages(List<Greeting> greetings);

    Greeting messageToGreeting(Message message);
}
