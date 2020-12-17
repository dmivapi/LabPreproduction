package com.epam.dmivapi.utils;

import com.epam.dmivapi.model.Author;
import com.epam.dmivapi.model.Book;
import com.epam.dmivapi.model.Genre;
import com.epam.dmivapi.model.Publisher;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class TestBooksGenerator {
    public List<Book> generateBooks(int count) {
        return IntStream.range(0, count)
                .mapToObj(TestBooksGenerator::createBook)
                .collect(Collectors.toList());
    }

    Book createBook(int counter) {
        return Book.builder()
                .id(counter)
                .build();
    }

    public List<Genre> generateGenres(int count) {
        return IntStream.range(0, count)
                .mapToObj(TestBooksGenerator::createGenre)
                .collect(Collectors.toList());
    }

    Genre createGenre(int seed) {
        return Genre.builder()
                .id(seed)
                .bookId(seed % 1000)
                .genreId(seed % 50)
                .languageId(seed % 2)
                .name("genre" + (seed % 50))
                .build();
    }

    public List<Publisher> generatePublishers(int count) {
        return IntStream.range(0, count)
                .mapToObj(TestBooksGenerator::createPublisher)
                .collect(Collectors.toList());
    }

    Publisher createPublisher(int seed) {
        return Publisher.builder()
                .id(seed)
                .name("publisher " + (seed % 500))
                .build();
    }

    public List<Author> generateAuthors(int count) {
        return IntStream.range(0, count)
                .mapToObj(TestBooksGenerator::createAuthor)
                .collect(Collectors.toList());
    }

    Author createAuthor(int seed) {
        return Author.builder()
                .id(seed)
                .firstName("firstName " + seed)
                .lastName("lastName " + seed)
                .build();
    }
}
