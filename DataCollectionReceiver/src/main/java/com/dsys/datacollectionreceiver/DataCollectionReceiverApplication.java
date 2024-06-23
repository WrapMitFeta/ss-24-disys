package com.dsys.datacollectionreceiver;

import com.dsys.datacollectionreceiver.controller.StationDataController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class DataCollectionReceiverApplication {
    static StationDataController collector = new StationDataController();

    public static void main(String[] args) throws IOException, TimeoutException {
        collector.run();
    }

}
