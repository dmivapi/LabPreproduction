package com.epam.dmivapi.springbootdemo;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping
@RestController
public class AuthorController {
    private final AuthorRepository repository;

    public AuthorController(AuthorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    List<Author> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    Author getOne(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

    @PostMapping
    public Author createAuthor() {
        Author author = new Author(
                RandomStringUtils.randomAlphabetic(8),
                RandomStringUtils.randomAlphabetic(8)
        );
        repository.save(author);

        return author;
    }

    @PutMapping
    public Author updateAuthor(@RequestBody Author author) {
        repository.save(author);
        return author;
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
