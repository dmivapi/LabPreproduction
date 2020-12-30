package com.epam.dmivapi.hibernate.repository;

import com.epam.dmivapi.hibernate.entity.BookCopy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public class BookCopyRepositoryImpl implements BookCopyRepository {
    SessionFactory sessionFactory;

    public BookCopyRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public BookCopy create(String title) {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setTitle(title);
        sessionFactory.getCurrentSession().save(bookCopy);

        return bookCopy;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookCopy> getByIds(List<UUID> ids) {
        Session session = sessionFactory.getCurrentSession();
        return session.byMultipleIds(BookCopy.class).multiLoad(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookCopy> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from BookCopy").list();
    }
}
