package com.epam.dmviapi.dao;

import org.springframework.stereotype.Repository;

@Repository
public class CDao {
    @Loggable
    public void findAll() {
        System.out.println("CDao::findAll invoked");
    }
    public void findOne() {
        System.out.println("CDao::findOne invoked");
    }
}
