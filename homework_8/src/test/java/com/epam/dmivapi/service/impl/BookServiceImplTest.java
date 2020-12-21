package com.epam.dmivapi.service.impl;

import com.epam.dmivapi.AbstractBaseTestCase;
import com.epam.dmivapi.model.Author;
import com.epam.dmivapi.model.Book;
import com.epam.dmivapi.model.Genre;
import com.epam.dmivapi.model.Publisher;
import com.epam.dmivapi.repository.BookRepository;
import com.epam.dmivapi.utils.TestBooksGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

class BookServiceImplTest extends AbstractBaseTestCase {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl sut;

    @Test
    void getBooksByTitleAndAuthor() {
        //Given
        final int NUM_OF_BOOKS = 11;
        List<Book> books = TestBooksGenerator.generateBooks(NUM_OF_BOOKS);
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
        //Given
        final int NUM_OF_BOOKS = 11;
        final int REC_PER_PAGE = 1;
        final int NUM_OF_PAGES = 11;
        doReturn(NUM_OF_BOOKS).
                when(bookRepository).countBooksByTitleAndAuthor(anyString(), anyString(), anyString());

        //When
        int numOfPages = sut.countBooksPagesByTitleAndAuthor("", "", "", REC_PER_PAGE);

        //Then
        verify(bookRepository).countBooksByTitleAndAuthor(anyString(), anyString(), anyString());
        assertThat(numOfPages, is(NUM_OF_PAGES));
    }

    @Test
    void getGenresByLanguageCode() {
        //Given
        final int NUM_OF_GENRES = 11;
        List<Genre> genres = TestBooksGenerator.generateGenres(NUM_OF_GENRES);
        doReturn(genres).
                when(bookRepository).findGenresByLanguageCode(anyString());

        //When
        List<Genre> foundGenres = sut.getGenresByLanguageCode("ru");

        //Then
        verify(bookRepository).findGenresByLanguageCode(anyString());
        assertThat(foundGenres, hasSize(NUM_OF_GENRES));

        genres.forEach(genre -> assertThat(foundGenres, hasItem(allOf(
                hasProperty("id", is(genre.getId())),
                hasProperty("bookId", is(genre.getBookId())),
                hasProperty("languageId", is(genre.getLanguageId())),
                hasProperty("genreId", is(genre.getGenreId())),
                hasProperty("name", is(genre.getName()))
        ))));
    }

    @Test
    void getAllPublishers() {
        //Given
        final int NUM_OF_PUBLISHERS = 11;
        List<Publisher> publishers = TestBooksGenerator.generatePublishers(NUM_OF_PUBLISHERS);
        doReturn(publishers).
                when(bookRepository).findAllPublishers();

        //When
        List<Publisher> foundPublishers = sut.getAllPublishers();

        //Then
        verify(bookRepository).findAllPublishers();
        assertThat(foundPublishers, hasSize(NUM_OF_PUBLISHERS));

        publishers.forEach(publisher -> assertThat(foundPublishers, hasItem(allOf(
                hasProperty("id", is(publisher.getId())),
                hasProperty("name", is(publisher.getName()))
        ))));
    }

    @Test
    void getAllAuthors() {
        //Given
        final int NUM_OF_AUTHORS = 11;
        List<Author> authors = TestBooksGenerator.generateAuthors(NUM_OF_AUTHORS);
        doReturn(authors).
                when(bookRepository).findAllAuthors();

        //When
        List<Author> foundAuthors = sut.getAllAuthors();

        //Then
        verify(bookRepository).findAllAuthors();
        assertThat(foundAuthors, hasSize(NUM_OF_AUTHORS));

        authors.forEach(author -> assertThat(foundAuthors, hasItem(allOf(
                hasProperty("id", is(author.getId())),
                hasProperty("lastName", is(author.getLastName())),
                hasProperty("firstName", is(author.getFirstName()))
        ))));
    }
}