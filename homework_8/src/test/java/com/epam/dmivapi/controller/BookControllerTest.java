package com.epam.dmivapi.controller;

import com.epam.dmivapi.AbstractBaseTestCase;
import com.epam.dmivapi.ContextParam;
import com.epam.dmivapi.Path;
import com.epam.dmivapi.config.LocaleConfig;
import com.epam.dmivapi.model.Author;
import com.epam.dmivapi.model.Book;
import com.epam.dmivapi.model.Genre;
import com.epam.dmivapi.model.Publisher;
import com.epam.dmivapi.service.BookService;
import com.epam.dmivapi.utils.TestBooksGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class BookControllerTest extends AbstractBaseTestCase {
    @Mock
    private BookService bookService;

    @Mock
    private LocaleConfig localeConfig;

    @InjectMocks
    private BookController sut;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    }

    @Test
    public void getBooksByTitleAndAuthor() throws Exception {
        final int BOOK_LIST_SIZE = 9;
        final int NUMBER_OF_PAGES = 1;
        //Given
        List<Book> books = TestBooksGenerator.generateBooks(BOOK_LIST_SIZE);
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

        List<Author> authors = TestBooksGenerator.generateAuthors(NUM_OF_AUTHORS);
        List<Publisher> publishers = TestBooksGenerator.generatePublishers(NUM_OF_PUBLISHERS);
        List<Genre> genres = TestBooksGenerator.generateGenres(NUM_OF_GENRES);

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
    public void createBook() throws Exception {
        doReturn("ru")
                .when(localeConfig)
                .getCurrentLocale(any());

        //When
        mockMvc.perform(post("/book/add"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(Command.LIST_BOOKS.getSystemName()));

        //Then
        verify(bookService, times(1)).createBook(any(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyString(), anyString(), anyInt());
    }

    @Test
    public void deleteBook() throws Exception {
        //Given
        List<String> publicationIds = Arrays.asList("7", "6", "5");
        final String SENDER_PAGE = "senderPage";

        //When
        mockMvc.perform(post("/book/delete")
                .param(ContextParam.PUBLICATIONS_IDS_TO_PROCESS, publicationIds.toString())
                .param(ContextParam.SELF_COMMAND, SENDER_PAGE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(SENDER_PAGE));

        //Then
        verify(bookService).deleteBook(anyList());
    }
}