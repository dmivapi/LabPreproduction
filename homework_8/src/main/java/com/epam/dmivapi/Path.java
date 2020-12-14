package com.epam.dmivapi;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j;

/**
 * Provides constants with paths and some  utility methods
 * to work with them
 */
@Log4j
@UtilityClass
public class Path {
    // pages
    public final String PAGE__ENTER_USER_INFO = "enterUserInfo";
    public final String PAGE__ENTER_BOOK_INFO = "enterBookInfo";
    public final String PAGE__ERROR = "error_page";

    public final String PAGE__LIST_BOOKS = "listBooks";
    public final String PAGE__LIST_LOANS_SINGLE_USER = "listLoansSingleUser";
    public final String PAGE__LIST_LOANS_MULTIPLE_USERS = "listLoansMultipleUsers";
    public final String PAGE__LIST_USERS_READERS_FOR_LIBRARIAN = "listUsersReadersForLibrarian";
    public final String PAGE__LIST_USERS_READERS_FOR_ADMIN = "listUsersReadersForAdmin";
    public final String PAGE__LIST_USERS_LIBRARIANS = "listUsersLibrarians";
}
