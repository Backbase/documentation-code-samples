package com.backbase.example.service;

import com.backbase.example.domain.Greeting;
import com.backbase.example.repository.GreetingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class GreetingsServiceImpl implements GreetingsService {

    private static final Logger log = LoggerFactory.getLogger(GreetingsServiceImpl.class);

    @Autowired
    private GreetingsRepository greetingsRepository;

    @Override
    @PreAuthorize("permitAll()")
    public List<Greeting> getGreetings() {
        log.debug("Service getGreetings is called {}", 1);
        return greetingsRepository.findAll();
    }

    @Override
    @PreAuthorize("permitAll()")
    public Greeting getGreetingById(String id) {
        log.debug("Service getGreetingById is called {}", id);
        return greetingsRepository.findById(id).get();
    }

    @Override
    @PreAuthorize("permitAll()")
    @Transactional
    public void addNewGreeting(Greeting greeting) {
        log.debug("Service addNewGreeting is called {}", 1);
        greetingsRepository.save(greeting);
    }
}
