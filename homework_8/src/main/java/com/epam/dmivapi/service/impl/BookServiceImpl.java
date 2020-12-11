package com.epam.dmivapi.service.impl;

import com.epam.dmivapi.model.Book;
import com.epam.dmivapi.repository.BookRepository;
import com.epam.dmivapi.service.BookService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getBooksByTitleAndAuthor(
            String title,
            String author,
            String genreLanguageCode,
            String orderByField,
            boolean isAscending,
            int currentPage,
            int recordsPerPage
    ) {
        log.debug("method invoked");
        if (recordsPerPage == 0) {
            throw new IllegalArgumentException("The number of records per page can not be 0");
        }
        return bookRepository.findBooksByTitleAndAuthor(
                title,
                author,
                genreLanguageCode,
                orderByField,
                isAscending,
                currentPage,
                recordsPerPage
        );
    }

    @Override
    public int countBooksPagesByTitleAndAuthor(
            String title,
            String author,
            String genreLanguageCode,
            int recordsPerPage
    ) {
        log.debug("method invoked");
        if (recordsPerPage == 0) {
            throw new IllegalArgumentException("The number of records per page can not be 0");
        }
        return ServiceUtils.calculateNumOfPages(
                bookRepository.countBooksByTitleAndAuthor(title, author, genreLanguageCode),
                recordsPerPage
        );
    }

    @Override
    public void createBook(Book book, int authorId, int publisherId, int genreId, int year, int price, String languageCode, String libCodeBase, int quantity) {
        log.debug("method invoked");
        bookRepository.createBook(book, authorId, publisherId, genreId, year, price, languageCode, generateLibCodes(libCodeBase, quantity));
    }

    private List<String> generateLibCodes(String base, int quantity) {
        List<String> codes = new ArrayList<>(quantity);
        for (int i = 0; i < quantity; i++) {
            codes.add(base + "." + i);
        }
        return codes;
    }

    @Override
    public void deleteBook(List<String> publicationIds) {
        log.debug("method invoked");
        bookRepository.deleteBook(
                publicationIds.stream().map(Integer::valueOf).collect(Collectors.toList())
        );
    }
}