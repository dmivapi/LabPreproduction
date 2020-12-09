package com.epam.dmivapi.controller;

import org.apache.log4j.Logger;


public enum Command {
    SWITCH_LOCALE("/app/switchLocale"),

    ENTER_READER("/guest/enter/reader"),
    ENTER_LIBRARIAN("/admin/enter/librarian"),
    ENTER_BOOK_INFO("/book/enter"),

    LOGIN("/book/list"),
    LOGOUT("logout"),
    CREATE_READER("/guest/create/reader"),
    CREATE_LIBRARIAN("/admin/create/librarian"),
    UPDATE_READER_BLOCKED("/admin/update/readerblocked"),
    DELETE_LIBRARIAN("/admin/delete/librarian"),

    LIST_BOOKS("/book/list"),
    BOOK_NEW("/book/new"),
    BOOK_REMOVE("/book/remove"),

    LIST_LOANS_OF_SELF("/loan/self"),
    LIST_LOANS_OF_USER("/loan/"),
    LIST_LOANS_OF_ALL("/loan"),

    LIST_USERS_LIBRARIANS("/admin/list/librarians"),
    LIST_USERS_READERS_FOR_ADMIN("/admin/list/readers"),
    LIST_USERS_READERS_FOR_LIBRARIAN("/librarian/list/readers"),

    LOAN_NEW("/loan/new"),
    LOAN_OUT("/loan/out"),
    LOAN_REMOVE("/loan/delete"),
    LOAN_IN("/loan/in");

    private static final Logger log = Logger.getLogger(Command.class);

    private final String systemName;


    Command(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }
}
