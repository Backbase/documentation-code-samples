package com.backbase.example.service;

import com.backbase.example.domain.Greeting;
import com.backbase.example.repository.GreetingsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GreetingsServiceImpl implements GreetingsService {

    @Autowired
    private GreetingsRepository greetingsRepository;

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("permitAll()")
    public List<Greeting> getGreetings() {
        return greetingsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("permitAll()")
    public Greeting getGreetingById(String id) {
        return greetingsRepository.findById(id).get();
    }

    @Override
    @Transactional
    @PreAuthorize("permitAll()")
    public void saveGreeting(Greeting greeting) {
        greetingsRepository.save(greeting);
    }

    @Override
    @Transactional
    @PreAuthorize("permitAll()")
    public void deleteGreeting(Greeting greeting) {
        greetingsRepository.delete(greeting);
    }


}
