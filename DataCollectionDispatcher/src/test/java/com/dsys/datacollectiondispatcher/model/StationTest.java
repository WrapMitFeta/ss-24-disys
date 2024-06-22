package com.dsys.datacollectiondispatcher.model;

import org.junit.jupiter.api.BeforeEach;

class StationTest {

    private Station stationUnderTest;

    @BeforeEach
    void setUp() {
        stationUnderTest = new Station(0, "db_url", 0.0f, 0.0f);
    }
}
