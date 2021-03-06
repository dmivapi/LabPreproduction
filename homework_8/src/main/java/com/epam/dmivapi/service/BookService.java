package com.epam.dmivapi.service;

import com.epam.dmivapi.model.Author;
import com.epam.dmivapi.model.Book;
import com.epam.dmivapi.model.Genre;
import com.epam.dmivapi.model.Publisher;

import java.util.List;

public interface BookService {
    List<Book> getBooksByTitleAndAuthor(
            String title,
            String author,
            String genreLanguageCode,
            String orderByField,
            boolean isAscending,
            int currentPage,
            int recordsPerPage
    );

    int countBooksPagesByTitleAndAuthor(
            String title,
            String author,
            String genreLanguageCode,
            int recordsPerPage
    );

    void createBook(Book book, int authorId, int publisherId, int genreId, int year, int price, String languageCode, String libCodeBase, int quantity);
    void deleteBook(List<String> publicationIds);

    List<Genre> getGenresByLanguageCode(String languageCode);
    List<Publisher> getAllPublishers();
    List<Author> getAllAuthors();
}
