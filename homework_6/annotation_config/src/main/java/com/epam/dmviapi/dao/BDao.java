package com.epam.dmviapi.dao;

import org.springframework.stereotype.Repository;

@Repository
public class BDao {
    @Loggable
    void getAll() {
        System.out.println("BDao::getAll invoked");
    }
    void findOne() {
        System.out.println("BDao::findOne invoked");
    }
}
