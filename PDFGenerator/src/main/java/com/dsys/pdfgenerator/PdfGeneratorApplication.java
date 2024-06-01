package com.dsys.pdfgenerator;

import com.dsys.pdfgenerator.controller.PDFGeneratorController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class PdfGeneratorApplication {

    public static void main(String[] args) throws IOException, TimeoutException {
        PDFGeneratorController.run();
    }

}
