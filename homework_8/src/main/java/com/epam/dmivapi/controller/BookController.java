package com.epam.dmivapi.controller;

import com.epam.dmivapi.ContextParam;
import com.epam.dmivapi.model.Book;
import com.epam.dmivapi.Path;
import com.epam.dmivapi.repository.impl.BookRepositoryImpl;
import com.epam.dmivapi.service.BookService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(path = "/book")
@Log4j
public class BookController {
    private BookService bookService;
    private HttpSession session;

    @Autowired
    public BookController(BookService bookService, HttpSession session) {
        this.bookService = bookService;
        this.session = session;
    }

    @RequestMapping("/list")
    public String getBooksByTitleAndAuthor(
            @RequestParam(value = ContextParam.BK_TITLE, defaultValue = "") String title,
            @RequestParam(value = ContextParam.BK_AUTHOR, defaultValue = "") String author,
            @RequestParam(value = ContextParam.BS_ORDER_BY, defaultValue = "") String orderBy,
            @RequestParam(value = ContextParam.BS_ORDER_BY_DIRECTION, defaultValue = "") String orderByDir,
            @RequestParam(value = ContextParam.PGN_CURRENT_PAGE, defaultValue = "1") int currentPage,
            @RequestParam(value = ContextParam.PGN_RECORDS_PER_PAGE, defaultValue = ContextParam.RECORDS_PER_PAGE) int recordsPerPage,
            Model model
    ) {
        log.debug("/book/list invoked");
        final String GENRE_LANGUAGE_CODE = ContextParam.getCurrentLocale(session);

        List<Book> books = bookService.getBooksByTitleAndAuthor(
                title, author, GENRE_LANGUAGE_CODE,
                orderBy, !"DESC".equalsIgnoreCase(orderByDir),
                currentPage, recordsPerPage
        );
        model.addAttribute(ContextParam.BS_BOOKS, books);

        int nOfPages = bookService.countBooksPagesByTitleAndAuthor(
                title, author,
                GENRE_LANGUAGE_CODE,
                recordsPerPage
        );
        model.addAttribute(ContextParam.PGN_RECORDS_PER_PAGE, recordsPerPage);
        model.addAttribute(ContextParam.PGN_NUMBER_OF_PAGES, nOfPages);

        return Path.PAGE__LIST_BOOKS;
    }

    @RequestMapping("/enter")
    public String enterBook(Model model) {
        log.debug("/book/enter invoked");
        final String GENRE_LANGUAGE_CODE = ContextParam.getCurrentLocale(session);

        model.addAttribute(ContextParam.BK_AUTHORS, BookRepositoryImpl.findAuthors());
        model.addAttribute(ContextParam.BK_PUBLISHERS, BookRepositoryImpl.findPublishers());
        model.addAttribute(ContextParam.BK_GENRES, BookRepositoryImpl.findGenres(GENRE_LANGUAGE_CODE));

        return Path.PAGE__ENTER_BOOK_INFO;
    }

    @RequestMapping("/add")
    public String createBook(
            @RequestParam(value = ContextParam.BK_TITLE, defaultValue = "") String title,
            @RequestParam(value = ContextParam.BK_AUTHOR, defaultValue = "") int authorId,
            @RequestParam(value = ContextParam.BK_PUBLISHER, defaultValue = "") int publisherId,
            @RequestParam(value = ContextParam.BK_GENRE, defaultValue = "") int genreId,
            @RequestParam(value = ContextParam.BK_YEAR, defaultValue = "") int year,
            @RequestParam(value = ContextParam.BK_PRICE, defaultValue = "") int price,
            @RequestParam(value = ContextParam.BK_LIB_CODE_BASE, defaultValue = "") String libCodeBase,
            @RequestParam(value = ContextParam.BK_QUANTITY, defaultValue = "") int quantity
    ) {
        log.debug("/book/add invoked");
        final String GENRE_LANGUAGE_CODE = ContextParam.getCurrentLocale(session);
        Book book = new Book();
        book.setTitle(title);
        bookService.createBook(book, authorId, publisherId, genreId, year, price, GENRE_LANGUAGE_CODE, libCodeBase, quantity);

        return "redirect:" + Command.LIST_BOOKS.getSystemName();
    }

    @RequestMapping("/delete")
    public String deleteBook(
            @RequestParam(value = ContextParam.PUBLICATIONS_IDS_TO_PROCESS) List<String> publicationIds,
            @RequestParam(ContextParam.SELF_COMMAND) String senderPage) {
        log.debug("/log/delete invoked");
        bookService.deleteBook(publicationIds);

        return "forward:" + senderPage;
    }
}
