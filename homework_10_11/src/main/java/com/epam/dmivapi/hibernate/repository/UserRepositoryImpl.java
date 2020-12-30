package com.epam.dmivapi.hibernate.repository;

import com.epam.dmivapi.hibernate.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    SessionFactory sessionFactory;

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void save(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public User get(Integer userId) {
        return null;
    }

    @Override
    public User get(String email) {
        return null;
    }

    @Override
    public void delete(Integer userId) {

    }
}
