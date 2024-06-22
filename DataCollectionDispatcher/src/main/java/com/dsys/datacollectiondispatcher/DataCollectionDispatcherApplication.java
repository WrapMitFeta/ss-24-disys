package com.dsys.datacollectiondispatcher;

import com.dsys.datacollectiondispatcher.controller.DistpatchingController;
import com.dsys.datacollectiondispatcher.service.DatabaseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class DataCollectionDispatcherApplication {
	 static DistpatchingController dispatcher = new DistpatchingController();

	public static void main(String[] args) throws IOException, TimeoutException {
		dispatcher.run();
	}

}
