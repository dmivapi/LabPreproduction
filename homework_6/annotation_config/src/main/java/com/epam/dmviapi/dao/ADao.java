package com.epam.dmviapi.dao;

import org.springframework.stereotype.Repository;

@Repository
public class ADao {
    void findAll() {
        System.out.println("ADao::findAll invoked");
    }
    void findOne() {
        System.out.println("ADao::findOne invoked");
    }
}
