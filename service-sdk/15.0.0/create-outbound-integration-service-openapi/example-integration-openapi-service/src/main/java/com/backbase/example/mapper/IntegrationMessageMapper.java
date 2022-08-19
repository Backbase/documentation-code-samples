package com.backbase.example.mapper;

import com.backbase.example.model.Message;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IntegrationMessageMapper {

    IntegrationMessageMapper INSTANCE = Mappers.getMapper( IntegrationMessageMapper.class);

    @Mapping(source = "messages.messageId", target = "id")
    com.backbase.example.api.client.v1.model.Message messageToIntegrationGetResponseBody(Message messages);

    List<com.backbase.example.api.client.v1.model.Message> messagesToIntegrationGetResponseBodys(List<Message> messages);
}
