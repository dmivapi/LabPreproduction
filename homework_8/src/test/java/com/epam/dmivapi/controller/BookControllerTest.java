package com.epam.dmivapi.controller;

import com.epam.dmivapi.ContextParam;
import com.epam.dmivapi.Path;
import com.epam.dmivapi.config.LocaleConfig;
import com.epam.dmivapi.model.Author;
import com.epam.dmivapi.model.Book;
import com.epam.dmivapi.model.Genre;
import com.epam.dmivapi.model.Publisher;
import com.epam.dmivapi.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.epam.dmivapi.utils.TestBooksGenerator.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class BookControllerTest {
    @Mock
    private BookService bookService;

    @Mock
    private LocaleConfig localeConfig;

    @Mock
    private MockHttpSession session;

    @InjectMocks
    private BookController sut;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    }

    @Test
    public void getBooksByTitleAndAuthor() throws Exception {
        final int BOOK_LIST_SIZE = 9;
        final int NUMBER_OF_PAGES = 1;
        //Given
        List<Book> books = generateBooks(BOOK_LIST_SIZE);
        doReturn("ru")
                .when(localeConfig)
                .getCurrentLocale(any());

        doReturn(books)
                .when(bookService)
                .getBooksByTitleAndAuthor(
                        anyString(), anyString(), anyString(), anyString(), anyBoolean(), anyInt(), anyInt());

        doReturn(NUMBER_OF_PAGES)
                .when(bookService)
                .countBooksPagesByTitleAndAuthor(
                        anyString(), anyString(), anyString(), eq(BOOK_LIST_SIZE));

        //When
        mockMvc.perform(post("/book/list")
                .param(ContextParam.PGN_RECORDS_PER_PAGE,String.valueOf(BOOK_LIST_SIZE)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(Path.PAGE__LIST_BOOKS))
                .andExpect(forwardedUrl(Path.PAGE__LIST_BOOKS))
                .andExpect(model().attribute(ContextParam.BS_BOOKS, hasSize(BOOK_LIST_SIZE)))
                .andExpect(model().attribute(ContextParam.BS_BOOKS, containsInRelativeOrder(books.toArray())))
                .andExpect(model().attribute(ContextParam.PGN_RECORDS_PER_PAGE, BOOK_LIST_SIZE))
                .andExpect(model().attribute(ContextParam.PGN_NUMBER_OF_PAGES, NUMBER_OF_PAGES)
                );

        //Then
        verify(bookService, times(1)).getBooksByTitleAndAuthor(
                anyString(), anyString(), anyString(), anyString(), anyBoolean(), anyInt(), anyInt());
        verify(bookService, times(1)).countBooksPagesByTitleAndAuthor(
                anyString(), anyString(), anyString(), anyInt());

    }

    @Test
    public void enterBook() throws Exception {
        //Given
        final int NUM_OF_AUTHORS = 25;
        final int NUM_OF_PUBLISHERS = 15;
        final int NUM_OF_GENRES = 10;

        List<Author> authors = generateAuthors(NUM_OF_AUTHORS);
        List<Publisher> publishers = generatePublishers(NUM_OF_PUBLISHERS);
        List<Genre> genres = generateGenres(NUM_OF_GENRES);

        doReturn("ru")
                .when(localeConfig)
                .getCurrentLocale(any());

        doReturn(authors)
                .when(bookService)
                .getAllAuthors();

        doReturn(publishers)
                .when(bookService)
                .getAllPublishers();

        doReturn(genres)
                .when(bookService)
                .getGenresByLanguageCode(anyString());

        //When
        mockMvc.perform(post("/book/enter"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(Path.PAGE__ENTER_BOOK_INFO))
                .andExpect(model().attribute(ContextParam.BK_AUTHORS, hasSize(NUM_OF_AUTHORS)))
                .andExpect(model().attribute(ContextParam.BK_AUTHORS, contains(authors.toArray())))
                .andExpect(model().attribute(ContextParam.BK_PUBLISHERS, hasSize(NUM_OF_PUBLISHERS)))
                .andExpect(model().attribute(ContextParam.BK_PUBLISHERS, containsInAnyOrder(publishers.toArray())))
                .andExpect(model().attribute(ContextParam.BK_GENRES, hasSize(NUM_OF_GENRES)))
                .andExpect(model().attribute(ContextParam.BK_GENRES, containsInAnyOrder(genres.toArray())));

        //Then
        verify(bookService, times(1)).getAllAuthors();
        verify(bookService, times(1)).getAllPublishers();
        verify(bookService, times(1)).getGenresByLanguageCode(anyString());
    }

    @Test
    @Disabled("Not Implemented yet")
    public void createBook() {
    }

    @Test
    @Disabled("Not Implemented yet")
    public void deleteBook() {
    }
}