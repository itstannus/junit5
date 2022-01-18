package com.example.junit5;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public abstract class AbstractContainerBaseTest {

    protected static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer("mysql:latest")
                .withUsername("test")
                .withPassword("test")
                .withDatabaseName("test");
        MY_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", AbstractContainerBaseTest.MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", AbstractContainerBaseTest.MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", AbstractContainerBaseTest.MY_SQL_CONTAINER::getPassword);
    }
}

