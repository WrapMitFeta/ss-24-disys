package com.dsys.datacollectionreceiver.controller;

import com.dsys.datacollectionreceiver.model.Station;
import com.dsys.datacollectionreceiver.service.MessageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StationDataControllerTest {
    @Mock
    MessageService messageService;



    @Test
    @DisplayName("Should send 2+Number of stations as message")
    public void shouldSend2NumberOfStationsAsMessage() throws Exception {
        StationDataController stationDataController = new StationDataController(messageService);
        doNothing().when(messageService).sendMessage(Mockito.anyString(), Mockito.anyString());

        ArrayList<Station> stations = new ArrayList<>();
        Station station1 = new Station("1", "5");
        Station station2 = new Station("2", "6");
        Station station3 = new Station("3", "7");
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);


        stationDataController.collect(new String[]{"to", "start"});
        stationDataController.collect(new String[]{"to", station1.getId(), station1.getId()});
        stationDataController.collect(new String[]{"to", station2.getId(), station2.getId()});
        stationDataController.collect(new String[]{"to", station3.getId(), station3.getId()});
        stationDataController.collect(new String[]{"to", "finished"});


        verify(messageService, times(2+stations.size())).sendMessage(Mockito.anyString(), Mockito.anyString());
    }

}