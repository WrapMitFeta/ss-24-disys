package com.dsys.datacollectionreceiver.controller;

import com.dsys.datacollectionreceiver.model.CustomerStationData;
import com.dsys.datacollectionreceiver.model.Station;
import com.dsys.datacollectionreceiver.service.MessageService;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class StationDataController {
    private static CustomerStationData customerStationData;
    private static MessageService messageService = new MessageService();


    public  void run() throws IOException, TimeoutException {
        MessageService messageService = new MessageService();
        String[] subscribe = new String[1];
        subscribe[0] = "collection_receiver";
        messageService.listen(subscribe);
    }
    public static void collect(String message[]) throws Exception {
    if(message[1].equals("start")) customerStationData = new CustomerStationData(message[0], new ArrayList<>());

    else if(message[1].equals("finished")) {
        messageService.sendMessage("pdf_service", "start " + customerStationData.getCustomer_id());
        customerStationData.getStations().forEach(station -> {
            try {
                messageService.sendMessage("pdf_service", station.getId() + " " + station.getKwh());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        messageService.sendMessage("pdf_service", "end " + customerStationData.getCustomer_id());
    }

    else {
        Station newStation = new Station(message[1], message[2]);
        customerStationData.getStations().add(newStation);
    }
    }

}
