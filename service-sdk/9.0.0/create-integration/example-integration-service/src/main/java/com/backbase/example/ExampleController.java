package com.backbase.example;

import com.backbase.buildingblocks.presentation.errors.BadRequestException;
import com.backbase.buildingblocks.presentation.errors.InternalServerErrorException;
import com.backbase.example.integration.MessageClient;
import com.backbase.example.mapper.IntegrationMessageMapper;
import com.backbase.example.model.Message;
import com.backbase.integration.example.rest.spec.v1.example.ExampleApi;
import com.backbase.integration.example.rest.spec.v1.example.IntegrationGetResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;

@RestController
public class ExampleController implements ExampleApi {

    @Autowired
    private MessageClient messageClient;

    @Override
    public List<IntegrationGetResponseBody> getIntegration(HttpServletRequest httpServletRequest,
                                                     HttpServletResponse httpServletResponse)
            throws BadRequestException, InternalServerErrorException {

        List<Message> messages = null;
        try {
            messages = messageClient.getMessages();
        } catch (IOException e) {
            throw new InternalServerErrorException("Error has occurred sending message");
        }

        return IntegrationMessageMapper.INSTANCE.messagesToIntegrationGetResponseBodys(messages);
    }
}