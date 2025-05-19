package com.backbase.example.service;

import com.backbase.example.domain.Greeting;
import java.util.List;

public interface GreetingsService {

    List<Greeting> getGreetings();
    // tag::getExternalGreetings[]
    List<Greeting> getExternalGreetings();
    // end::getExternalGreetings[]
    Greeting getGreetingById(String id);

    void saveGreeting(Greeting greeting);

    void deleteGreeting(Greeting greeting);
}
