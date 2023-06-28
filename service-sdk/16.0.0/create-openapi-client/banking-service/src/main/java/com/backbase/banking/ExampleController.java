package com.backbase.banking;

import com.backbase.messaging.api.client.v2.MessageApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class ExampleController  {

    @Autowired
    private MessageApi messageApi;

    @RequestMapping(method = RequestMethod.GET, value = "/message", produces = {
                    "application/json"
    })
    @ResponseStatus(HttpStatus.OK)
    public Message getMessage() {
        return new Message("Hello World");
    }
}