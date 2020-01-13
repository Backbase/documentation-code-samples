package com.backbase.example.integration;

import com.backbase.example.model.Message;

import java.io.IOException;
import java.util.List;

public interface MessageClient {

    List<Message> getMessages() throws IOException;
}
