package com.epam.dmivapi.springbootdemo;

import org.springframework.data.jpa.repository.JpaRepository;

interface AuthorRepository extends JpaRepository<Author, Integer> {
}
