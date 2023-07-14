package com.backbase.example.service;

import com.backbase.example.domain.Greeting;
import com.backbase.example.repository.GreetingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GreetingsServiceImpl implements GreetingsService {

    @Autowired
    private GreetingsRepository greetingsRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Greeting> getGreetings() {
        return greetingsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Greeting getGreetingById(String id) {
        return greetingsRepository.findById(id).get();
    }

    @Override
    @Transactional
    public void saveGreeting(Greeting greeting) {
        greetingsRepository.save(greeting);
    }

    @Override
    @Transactional
    public void deleteGreeting(Greeting greeting) {
        greetingsRepository.delete(greeting);
    }


}
