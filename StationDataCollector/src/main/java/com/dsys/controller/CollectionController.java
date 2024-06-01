package com.dsys.controller;

import com.dsys.model.Station;
import com.dsys.service.DatabaseService;
import com.dsys.service.MessageService;

import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class CollectionController {
    private static DatabaseService databaseService = new DatabaseService();
    private static MessageService messageService = new MessageService();
    private static int index = 1;
    public static void run() throws IOException, TimeoutException {
        String[] subscribe = new String[1];
        subscribe[0] = "data_collector";
        messageService.listen(subscribe);
    }
    public static void collect(String customer_id ,String message) throws SQLException {
        ArrayList<Station> stations = null;
        if(message.equals("end")) finalize(customer_id);
        else stations =  databaseService.getStations(customer_id, message);
        if(stations != null) {
            stations.forEach(station -> {
                try {
                    messageService.sendMessage("collection_receiver", index +  " " + station.getKwh(), customer_id);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            index++;
        }
    };
    public static void finalize(String customer_id) {
        index = 1;
        try {
            messageService.sendMessage("collection_receiver", "finished", customer_id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
