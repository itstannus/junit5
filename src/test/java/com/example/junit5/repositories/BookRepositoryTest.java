package com.example.junit5.repositories;

import com.example.junit5.AbstractContainerBaseTest;
import com.example.junit5.entities.Book;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Book repository integration tests")
class BookRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private BookRepository bookRepository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", AbstractContainerBaseTest.MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", AbstractContainerBaseTest.MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", AbstractContainerBaseTest.MY_SQL_CONTAINER::getPassword);
    }

    @Test
    @DisplayName("Save book in DB")
    @Rollback(false)
    @Order(1)
    void shouldSaveBook() {
//        assumeTrue(AbstractContainerBaseTest.MY_SQL_CONTAINER.isRunning());
        Book book = new Book();
        book.setName("Head First Java");
        Book savedBook = bookRepository.save(book);

        assertThat(savedBook).as("Book should be saved.")
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(book);
    }

    @Test
    @DisplayName("Find by book name")
    @Order(2)
    void shouldFindBookByName(){
        Book book = bookRepository.findByName("Head First Java");

        assertThat(book).as("Find book by name should work once book is saved.")
                .isNotNull();

    }

    @Test
    @DisplayName("List all books currently in DB")
    @Order(3)
    void shouldListAllBooks(){
        List<Book> books = bookRepository.findAll();
        Book book = new Book();
        book.setName("Head First Java");

        assertThat(books).as("Should have one book in DB with book name : Head First Java" )
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(book);


    }

    @Test
    @DisplayName("Should update book name")
    @Order(4)
    @Rollback(false)
    void shouldUpdateBook(){
        Book book = new Book();
        book.setId(1L);
        book.setName("Design Patterns");
        Book updatedBook = bookRepository.save(book);
        assertThat(updatedBook).as("Book name should be updated.")
                .usingRecursiveComparison()
                .isEqualTo(book);
    }

    @Test
    @DisplayName("Should delete the book in DB")
    @Order(5)
    @Rollback(false)
    void shouldDeleteBook(){
        long bookId=1L;
        assertThat(bookRepository.findById(bookId)).isNotNull().as("Book should be present before delete.");
        bookRepository.deleteById(bookId);
        assertThat(bookRepository.findById(bookId)).isEmpty().as("Book should NOT be present after delete");
    }
}