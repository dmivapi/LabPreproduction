package com.epam.dmivapi.dao;

import org.springframework.stereotype.Repository;

@Repository
public class ADao {
    public void findAll() {
        System.out.println("ADao::findAll invoked");
    }
    public void findOne() {
        System.out.println("ADao::findOne invoked");
    }
}
