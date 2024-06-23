package com.dsys;

import com.dsys.controller.CollectionController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class StationDataCollectorApplication {
    static CollectionController collectionController = new CollectionController();

    public static void main(String[] args) throws IOException, TimeoutException {
        collectionController.run();
    }

}
