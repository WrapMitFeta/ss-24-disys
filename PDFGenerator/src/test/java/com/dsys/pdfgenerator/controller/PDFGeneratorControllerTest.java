package com.dsys.pdfgenerator.controller;

import com.dsys.pdfgenerator.model.Customer;
import com.dsys.pdfgenerator.model.Print;
import com.dsys.pdfgenerator.model.StationBillingList;
import com.itextpdf.text.DocumentException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PDFGeneratorControllerTest {

    @Test
    void testRun() throws Exception {
        // Setup
        // Run the test
        PDFGeneratorController.run();

        // Verify the results
    }



    @Test
    void testGenerate() throws Exception {
        // Setup
        final Print print = new Print(new Customer(1, "first_name", "last_name"),
                new ArrayList<>(List.of(new StationBillingList("station_id", new ArrayList<>(List.of("12","14", "16"))))));

        // Run the test
        PDFGeneratorController.generate(print);

        // Verify the results
    }

}
