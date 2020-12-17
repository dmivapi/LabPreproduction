package com.epam.dmivapi.repository;

import com.epam.dmivapi.model.Author;
import com.epam.dmivapi.model.Book;
import com.epam.dmivapi.model.Genre;
import com.epam.dmivapi.model.Publisher;

import java.util.List;

public interface BookRepository {
    List<Book> findBooksByTitleAndAuthor(
            String title,
            String author,
            String genreLanguageCode,
            String orderByField,
            boolean isAscending,
            int currentPage,
            int recordsPerPage
    );

    int countBooksByTitleAndAuthor(
            String title,
            String author,
            String genreLanguageCode
    );

    void createBook(Book book, int authorId, int publisherId, int genreId, int year, int price, String languageCode, List<String> libCodes);
    void deleteBook(List<Integer> publicationIds);

    List<Genre> findGenresByLanguageCode(String languageCode);
    List<Publisher> findAllPublishers();
    List<Author> findAllAuthors();
}
