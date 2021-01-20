package com.epam.dmivapi.awsdemo;

import org.springframework.data.jpa.repository.JpaRepository;

interface AuthorRepository extends JpaRepository<Author, Integer> {
}
