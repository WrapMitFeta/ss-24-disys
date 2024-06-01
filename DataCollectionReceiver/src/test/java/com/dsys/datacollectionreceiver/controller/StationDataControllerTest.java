package com.dsys.datacollectionreceiver.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StationDataControllerTest {

    private StationDataController stationDataControllerUnderTest;

    @BeforeEach
    void setUp() {
        stationDataControllerUnderTest = new StationDataController();
    }

    @Test
    void testRun() throws Exception {
        // Setup
        // Run the test
        stationDataControllerUnderTest.run();

        // Verify the results
    }


}
