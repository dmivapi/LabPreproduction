package com.epam.dmivapi.controller;

import com.epam.dmivapi.ContextParam;
import com.epam.dmivapi.Path;
import com.epam.dmivapi.model.Book;
import com.epam.dmivapi.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;

import static com.epam.dmivapi.utils.TestBooksGenerator.generateBooks;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookControllerTest {

    private final BookService bookService = Mockito.mock(BookService.class);
    private final MockHttpSession session = new MockHttpSession();
    private final BookController sut = new BookController(bookService, session);

    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();

    @Test
    void getBooksByTitleAndAuthor() throws Exception {
        final int BOOK_LIST_SIZE = 9;
        final int NUMBER_OF_PAGES = 1;
        //Given
        List<Book> books = generateBooks(BOOK_LIST_SIZE);
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
    void enterBook() {
    }

    @Test
    void createBook() {
    }

    @Test
    void deleteBook() {
    }
}