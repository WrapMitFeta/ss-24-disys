package com.dsys.controller;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CollectionControllerTest {

    @Test
    void testRun() throws Exception {
        // Setup
        // Run the test
        CollectionController.run();

        // Verify the results
    }



    @Test
    void testCollect_ThrowsSQLException() {
        // Setup
        // Run the test
        assertThatThrownBy(() -> CollectionController.collect("customer_id", "message"))
                .isInstanceOf(SQLException.class);
    }

    @Test
    void testFinalize() {
        // Setup
        // Run the test
        CollectionController.finalize("customer_id");

        // Verify the results
    }
}
