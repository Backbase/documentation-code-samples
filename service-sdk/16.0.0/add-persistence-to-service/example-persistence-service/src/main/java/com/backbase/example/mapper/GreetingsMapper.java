package com.backbase.example.mapper;

import com.backbase.example.Message;
import com.backbase.example.domain.Greeting;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy= ReportingPolicy.ERROR)
public interface GreetingsMapper {

    GreetingsMapper INSTANCE = Mappers.getMapper( GreetingsMapper.class);

    Message greetingToMessage(Greeting greeting);
    List<Message> greetingsToMessages(List<Greeting> greetings);
    Greeting messageToGreeting(Message message);
}
