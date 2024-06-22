package com.dsys.datacollectiondispatcher.service;

import com.dsys.datacollectiondispatcher.model.Station;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.sql.SQLException;
import java.util.ArrayList;



@ExtendWith(MockitoExtension.class)
class DatabaseServiceTest {
    static ArrayList<Station> stations = new ArrayList<>();

    @Mock
    static Station station1;
    @Mock
    static Station station2;
    @Mock
    static Station station3;

    @BeforeAll
    static void setUp() {
        station1 = Mockito.mock(Station.class);
        station2 = Mockito.mock(Station.class);
        station3 = Mockito.mock(Station.class);
        // (1, 'localhost:30011', '48.184192', '16.378604'),
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);
    }

    @Test
    @DisplayName("Should return Array of Stations")
    public void checkStationReturn() throws SQLException {
        DatabaseService databaseService = new DatabaseService();

        Assertions.assertInstanceOf(ArrayList.class,
                databaseService.getStations());
    }

}