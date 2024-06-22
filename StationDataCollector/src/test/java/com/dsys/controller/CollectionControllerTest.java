package com.dsys.controller;

import com.dsys.model.Station;
import com.dsys.service.DatabaseService;
import com.dsys.service.MessageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CollectionControllerTest {
    @Mock
    DatabaseService databaseService;
    @Mock
    MessageService messageService;


    @Test
    @DisplayName("Should send 1 + number of messages")
    void shouldSend1NumberOfMessages() throws Exception {
        ArrayList<Station> stations = new ArrayList<>();
        stations.add(new Station(1, 200, 1));
        stations.add(new Station(2, 200, 2));
        stations.add(new Station(3, 200, 3));
        CollectionController collectionController = new CollectionController(databaseService, messageService);
        doNothing().when(messageService).sendMessage(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        when(databaseService.getStations(Mockito.any(), Mockito.any())).thenReturn(stations);

        collectionController.collect("1", "");
        collectionController.collect("1", "end");
        verify(messageService, times(1+stations.size())).sendMessage(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }
}