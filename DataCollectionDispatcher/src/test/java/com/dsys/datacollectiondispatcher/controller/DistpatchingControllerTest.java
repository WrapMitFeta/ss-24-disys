package com.dsys.datacollectiondispatcher.controller;

import com.dsys.datacollectiondispatcher.model.Station;
import com.dsys.datacollectiondispatcher.service.DatabaseService;
import com.dsys.datacollectiondispatcher.service.MessageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DistpatchingControllerTest {
    @Mock
    DatabaseService databaseService;
    @Mock
    MessageService messageService;



    @Test
    @DisplayName("Should send Number of Stations +2 Messages ")
    public void shouldSendNumberOfStationsMessages() throws Exception {
        DistpatchingController dispatcher = new DistpatchingController(messageService, databaseService);
        ArrayList<Station> stations = new ArrayList<>();;

        stations.add(new Station(1, "", 100,200));
        stations.add(new Station(2, "", 100,200));
        stations.add(new Station(3, "", 100,200));



        when(databaseService.getStations()).thenReturn(stations);
        doNothing().when(messageService).sendMessage(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());

        dispatcher.dispatch("1");
        verify(messageService, times(2+stations.size())).sendMessage(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());

    }

}