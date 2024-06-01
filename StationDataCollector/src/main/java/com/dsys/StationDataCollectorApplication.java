package com.dsys;

import com.dsys.controller.CollectionController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class StationDataCollectorApplication {

    public static void main(String[] args) throws IOException, TimeoutException {
        CollectionController.run();
    }

}
