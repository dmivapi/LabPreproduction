package com.epam.dmivapi.utils;

import com.epam.dmivapi.model.Book;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class TestBooksGenerator {
    public static List<Book> generateBooks(int count) {
        return IntStream.range(0, count)
                .mapToObj(TestBooksGenerator::createBook)
                .collect(Collectors.toList());
    }

    Book createBook(int counter) {
        return Book.builder()
                .id(counter)
                .build();
    }
}
