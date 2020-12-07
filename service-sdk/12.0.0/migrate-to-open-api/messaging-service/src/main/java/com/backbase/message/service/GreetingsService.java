package com.backbase.message.service;

import com.backbase.message.domain.Greeting;

import java.util.List;

public interface GreetingsService {

    List<Greeting> getGreetings();

    Greeting getGreetingById(String id);

    void saveGreeting(Greeting greeting);

    void deleteGreeting(Greeting greeting);
}
