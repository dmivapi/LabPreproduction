package com.epam.dmivapi.service.impl.command;

import org.apache.log4j.Logger;


public enum Command {
    SWITCH_LOCALE("switchLocale"),

    ENTER_USER_INFO("enterUserInfo"),
    ENTER_BOOK_INFO("enterBookInfo"),

    LOGIN("/book/list"),
    REGISTER("register"),
    LOGOUT("logout"),
    USER_NEW("userNew"),
    USER_BLOCK("userBlock"),
    USER_REMOVE("userRemove"),

    LIST_BOOKS("/book/list"),
    BOOK_NEW("/book/new"),
    BOOK_REMOVE("/book/remove"),

    LIST_LOANS_OF_SELF("/loan/self"),
    LIST_LOANS_OF_USER("/loan/"),
    LIST_LOANS_OF_ALL("/loan"),

    LIST_USERS_LIBRARIANS("/user/admin/librarians"),
    LIST_USERS_READERS_FOR_ADMIN("/user/admin/borrowers"),
    LIST_USERS_READERS_FOR_LIBRARIAN("/user/librarian/borrowers"),

    LOAN_NEW("/loan/new"),
    LOAN_OUT("/loan/out"),
    LOAN_REMOVE("/loan/delete"),
    LOAN_IN("/loan/in");

    private static final Logger log = Logger.getLogger(Command.class);

    public final String systemName;


    Command(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }
}
