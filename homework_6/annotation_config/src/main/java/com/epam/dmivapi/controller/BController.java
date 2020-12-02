package com.epam.dmivapi.controller;

import org.springframework.stereotype.Component;

@Component
public class BController {
    public void getById(Long id) {
        System.out.println("BController::getById invoked");
    }

    public void getByName(String name) {
        System.out.println("BController::getById invoked");
    }
}
