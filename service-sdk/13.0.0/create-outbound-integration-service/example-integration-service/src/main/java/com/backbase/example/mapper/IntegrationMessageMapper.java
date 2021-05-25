package com.backbase.example.mapper;

import com.backbase.example.model.Message;
import com.backbase.integration.example.rest.spec.v1.example.IntegrationGetResponseBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IntegrationMessageMapper {

    IntegrationMessageMapper INSTANCE = Mappers.getMapper( IntegrationMessageMapper.class);

    @Mapping(source = "messages.messageId", target = "id")
    IntegrationGetResponseBody messageToIntegrationGetResponseBody(Message messages);

    List<IntegrationGetResponseBody> messagesToIntegrationGetResponseBodys(List<Message> messages);
}
