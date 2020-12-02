package com.epam.dmviapi.controller;

import org.springframework.stereotype.Controller;

@Controller
public class BController {
    public void getById(Long id) {
        System.out.println("BController::getById invoked");
    }

    public void getByName(String name) {
        System.out.println("BController::getById invoked");
    }
}
