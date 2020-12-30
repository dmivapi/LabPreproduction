package com.epam.dmivapi.hibernate.repository;

import com.epam.dmivapi.hibernate.entity.BookCopy;

import java.util.List;
import java.util.UUID;

public interface BookCopyRepository {
    BookCopy create(String title); // for UT only!
    List<BookCopy> getByIds(List<UUID> ids);
    List<BookCopy> getAll();
}
