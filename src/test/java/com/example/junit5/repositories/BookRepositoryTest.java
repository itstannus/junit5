package com.example.junit5.repositories;

import com.example.junit5.entities.Book;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Testcontainers
@SpringBootTest
@DisplayName("Book repository integration tests")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Container
    private static final MySQLContainer container =new MySQLContainer("mysql:8.0.27")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @Test
    @DisplayName("Save book in DB")
    void testSave() {
        assumeTrue(container.isRunning());
        Book book = new Book();
        book.setName("Head First Java");
        Book savedBook = bookRepository.save(book);
        assertEquals(book.getName(), savedBook.getName(), "Book was not saved");

    }

}