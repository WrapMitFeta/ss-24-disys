package com.dsys.datacollectiondispatcher.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DistpatchingControllerTest {

    private DistpatchingController distpatchingControllerUnderTest;

    @BeforeEach
    void setUp() {
        distpatchingControllerUnderTest = new DistpatchingController();
    }

    @Test
    void testRun() throws Exception {
        // Setup
        // Run the test
        distpatchingControllerUnderTest.run();

        // Verify the results
    }


    @Test
    void testDispatch() throws Exception {
        // Setup
        // Run the test
        DistpatchingController.dispatch("customerId");

        // Verify the results
    }


}
