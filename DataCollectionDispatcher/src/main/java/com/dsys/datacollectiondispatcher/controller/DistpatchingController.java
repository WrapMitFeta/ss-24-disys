package com.dsys.datacollectiondispatcher.controller;

import com.dsys.datacollectiondispatcher.model.Station;
import com.dsys.datacollectiondispatcher.service.DatabaseService;
import com.dsys.datacollectiondispatcher.service.MessageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class DistpatchingController {
    private MessageService messageService;
    private DatabaseService databaseService;

    public DistpatchingController() {
        this.messageService = new MessageService(this);
        this.databaseService = new DatabaseService();
    }

    public DistpatchingController(MessageService messageService, DatabaseService databaseService) {
        this.messageService = messageService;
        this.databaseService = databaseService;
    }




    public void run() throws IOException, TimeoutException {
            String[] subscribedTo=new String[1];
            subscribedTo[0] = "dispatch";
            messageService.listen(subscribedTo);
    }

    public void dispatch(String customerId) throws Exception {
        ArrayList<Station> stations = databaseService.getStations();
        messageService.sendMessage("collection_receiver", "start", customerId);
        stations.forEach(station -> {
            try {
                messageService.sendMessage("data_collector", station.getDb_url(), customerId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        messageService.sendMessage("data_collector", "end", customerId);
    }
}
