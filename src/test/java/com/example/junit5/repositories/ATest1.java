package com.example.junit5.repositories;

import com.example.junit5.AbstractContainerBaseTest;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Order(2)
public class ATest1 extends AbstractContainerBaseTest {


    @Test
    void test1() {

    }

    @Test
    void test2() {

    }
}
