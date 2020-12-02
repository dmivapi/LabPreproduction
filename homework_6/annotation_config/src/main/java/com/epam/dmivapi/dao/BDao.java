package com.epam.dmivapi.dao;

import org.springframework.stereotype.Repository;

@Repository
public class BDao {
    @Loggable
    public void getAll() {
        System.out.println("BDao::getAll invoked");
    }
    public void findOne() {
        System.out.println("BDao::findOne invoked");
    }
}
