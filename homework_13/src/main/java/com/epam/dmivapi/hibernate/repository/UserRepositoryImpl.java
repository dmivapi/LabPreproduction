package com.epam.dmivapi.hibernate.repository;

import com.epam.dmivapi.hibernate.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    SessionFactory sessionFactory;

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(User user) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        session.save(user);
        tx.commit();
        session.close();
    }

    @Override
    public User get(UUID userId) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, userId);
        return Objects.requireNonNull(user, "User not found by id: " + userId);
    }

    @Override
    public User get(String email) {
        Session session = sessionFactory.openSession();
        Object singleResult = session
                .getNamedQuery("UserByEmail")
                .setParameter("email", email)
                .getSingleResult();
        return (User) singleResult;
    }

    @Override
    public void delete(UUID userId) {
        User user = get(userId);
        sessionFactory.getCurrentSession().delete(user);
    }
}
