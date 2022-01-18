package com.example.junit5;

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
}

