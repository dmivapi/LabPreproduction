package com.epam.dmivapi.service.impl;

import com.epam.dmivapi.model.Book;
import com.epam.dmivapi.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.epam.dmivapi.utils.TestBooksGenerator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl sut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getBooksByTitleAndAuthor() {
        //Given
        final int NUM_OF_BOOKS = 11;
        List<Book> books = generateBooks(NUM_OF_BOOKS);
        doReturn(books).
                when(bookRepository).findBooksByTitleAndAuthor(anyString(), anyString(), anyString(), anyString(), anyBoolean(), anyInt(), anyInt());

        //When
        List<Book> foundBooks = sut.getBooksByTitleAndAuthor("", "", "", "", true, 1, 50);

        //Then
        verify(bookRepository).findBooksByTitleAndAuthor(anyString(), anyString(), anyString(), anyString(), anyBoolean(), anyInt(), anyInt());
        assertThat(foundBooks, hasSize(NUM_OF_BOOKS));

        books.forEach(book -> assertThat(foundBooks, hasItem(allOf(
                hasProperty("id", is(book.getId())),
                hasProperty("authors", is(book.getAuthors())),
                hasProperty("title", is(book.getTitle())),
                hasProperty("genre", is(book.getGenre())),
                hasProperty("publisher", is(book.getPublisher())),
                hasProperty("year", is(book.getYear())),
                hasProperty("price", is(book.getPrice()))
        ))));
    }

    @Test
    void countBooksPagesByTitleAndAuthor() {
    }

    @Test
    void createBook() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void getGenresByLanguageCode() {
    }

    @Test
    void getAllPublishers() {
    }

    @Test
    void getAllAuthors() {
    }
}