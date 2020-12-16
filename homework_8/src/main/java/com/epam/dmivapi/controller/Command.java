package com.epam.dmivapi.controller;

public enum Command {
    SWITCH_LOCALE("/app/switchLocale"),

    ENTER_READER("/guest/enter/reader"),
    ENTER_LIBRARIAN("/admin/enter/librarian"),
    ENTER_BOOK_INFO("/book/enter"),

    LOGIN("/app/login"),
    LOGOUT("/logout"),
    ADD_READER("/guest/add/reader"),
    ADD_LIBRARIAN("/admin/add/librarian"),
    UPDATE_READER_BLOCKED("/admin/update/readerBlocked"),
    DELETE_LIBRARIAN("/admin/delete/librarian"),

    LIST_BOOKS("/book/list"),
    BOOK_NEW("/book/add"),
    BOOK_DELETE("/book/delete"),

    LIST_LOANS_OF_SELF("/loan/self"),
    LIST_LOANS_OF_USER("/loan/"),
    LIST_LOANS_OF_ALL("/loan"),

    LIST_USERS_LIBRARIANS("/admin/list/librarians"),
    LIST_USERS_READERS_FOR_ADMIN("/admin/list/readers"),
    LIST_USERS_READERS_FOR_LIBRARIAN("/librarian/list/readers"),

    LOAN_NEW("/loan/add"),
    LOAN_OUT("/loan/out"),
    LOAN_DELETE("/loan/delete"),
    LOAN_IN("/loan/in");


    private final String systemName;
    private Command(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }
}
