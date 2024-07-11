package com.backbase.example.repository;

import com.backbase.example.domain.Greeting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GreetingsRepository extends CrudRepository<Greeting, String> {

    @Override
    List<Greeting> findAll();
}
