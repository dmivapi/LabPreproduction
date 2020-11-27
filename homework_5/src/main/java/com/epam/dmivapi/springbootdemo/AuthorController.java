package com.epam.dmivapi.springbootdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthorController {
    private final AuthorRepository repository;

    public AuthorController(AuthorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/authors")
    List<Author> getAll() {
        return repository.findAll();
    }

    @GetMapping("/authors/{id}")
    Author getOne(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }
}
