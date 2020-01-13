package com.backbase.example.service;

import com.backbase.example.domain.Greeting;
import com.backbase.example.mapper.GreetingsMapper;
import com.backbase.example.repository.GreetingsRepository;
import com.backbase.integration.example.listener.client.v1.example.IntegrationExampleExampleClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GreetingsServiceImpl implements GreetingsService {

    @Autowired
    private GreetingsRepository greetingsRepository;

    @Autowired
    private IntegrationExampleExampleClient integrationExampleExampleClient;

    @Override
    public List<Greeting> getGreetings() {
        return greetingsRepository.findAll();
    }

    @Override
    public List<Greeting> getExternalGreetings() {
        return GreetingsMapper.INSTANCE.integrationMessageToGreeting(integrationExampleExampleClient.getIntegration().getBody());
    }

    @Override
    public Greeting getGreetingById(String id) {
        return greetingsRepository.findById(id).get();
    }

    @Override
    public void saveGreeting(Greeting greeting) {
        greetingsRepository.save(greeting);
    }

    @Override
    public void deleteGreeting(Greeting greeting) {
        greetingsRepository.delete(greeting);
    }
}
