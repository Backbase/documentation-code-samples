package com.backbase.example.service;

import com.backbase.example.domain.Greeting;
import com.backbase.example.mapper.GreetingsMapper;
import com.backbase.example.repository.GreetingsRepository;
import com.backbase.integration.example.client.v1.MessageApi;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GreetingsServiceImpl implements GreetingsService {

    @Autowired
    private GreetingsRepository greetingsRepository;

    @Autowired
    private MessageApi exampleIntegrationApiClient;

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("permitAll()")
    public List<Greeting> getGreetings() {
        return greetingsRepository.findAll();
    }

    // tag::getExternalGreetings[]
    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("permitAll()")
    public List<Greeting> getExternalGreetings() {
        return GreetingsMapper.INSTANCE.integrationMessageToGreeting(exampleIntegrationApiClient.getMessages());
    }
    // end::getExternalGreetings[]

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("permitAll()")
    public Greeting getGreetingById(String id) {
        return greetingsRepository.findById(id).get();
    }

    @Override
    @PreAuthorize("permitAll()")
    public void saveGreeting(Greeting greeting) {
        greetingsRepository.save(greeting);
    }

    @Override
    @PreAuthorize("permitAll()")
    public void deleteGreeting(Greeting greeting) {
        greetingsRepository.delete(greeting);
    }
}
