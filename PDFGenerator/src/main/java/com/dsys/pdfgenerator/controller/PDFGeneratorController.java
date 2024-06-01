package com.dsys.pdfgenerator.controller;

import com.dsys.pdfgenerator.model.Customer;
import com.dsys.pdfgenerator.model.Print;
import com.dsys.pdfgenerator.model.StationBillingList;
import com.dsys.pdfgenerator.service.DatabaseService;
import com.dsys.pdfgenerator.service.MessageService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class PDFGeneratorController {
    private static MessageService messageService = new MessageService();
    private static DatabaseService databaseService = new DatabaseService();
    private static Print print;
    public static void run() throws IOException, TimeoutException {
        String[] subscribe = new String[1];
        subscribe[0] = "pdf_service";
        messageService.listen(subscribe);
    }

    public static void print(String[] message) throws SQLException, DocumentException, FileNotFoundException {
        Customer customer;

        if (message[0].equals("start")) {
            customer = databaseService.getCustomer(message[1]);
           print = new Print(customer, new ArrayList<>());
        }
        else if (message[0].equals("end") ) generate(print);
        else {
            StationBillingList billings = new StationBillingList(message[0], new ArrayList<>());
            for(int i = 1; i < message.length; i++) billings.getKwh().add(message[i]);
            print.getBillings().add(billings);
        }

    }

    public static void generate(Print print) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Invoice " + print.getCustomer().getCustomer_id() + ".pdf"));
        String spacing = "       ";

        document.open();
        Font header = FontFactory.getFont(FontFactory.COURIER, 25, BaseColor.BLACK);
        Font subHeader = FontFactory.getFont(FontFactory.COURIER, 18, BaseColor.BLACK);
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

        // Create a Paragraph for the header line
        Paragraph headerParagraph = new Paragraph();
        headerParagraph.setFont(header);
        headerParagraph.add("Invoice for " + print.getCustomer().getFirst_name() + " " + print.getCustomer().getLast_name());
        headerParagraph.add(new Chunk("\n"));
        headerParagraph.setSpacingAfter(4f);
        document.add(headerParagraph);


        ArrayList<StationBillingList> billings = print.getBillings();
        String currentStation = "";
        double total = 0;
        for(StationBillingList billing : billings) {
            if(!currentStation.equals(billing.getStation_id())){
                currentStation= billing.getStation_id();
                Paragraph billingHeadingParagraph = new Paragraph();
                billingHeadingParagraph.setFont(subHeader);
                billingHeadingParagraph.setSpacingBefore(10f);
                billingHeadingParagraph.add("Billings for station " + billing.getStation_id());
                // Create a Paragraph for the billing heading


                try {
                    document.add(billingHeadingParagraph);
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                }
            }


            ArrayList<String> kwhs = billing.getKwh();
            double stationTotal = 0;
            for(String kwh : kwhs)  {
                // Create a Paragraph for each KWh amount
                Paragraph amountParagraph = new Paragraph();
                amountParagraph.setFont(font);
                amountParagraph.add(spacing + kwh + " kwh");
                amountParagraph.add(new Chunk("\n"));

                try {
                    document.add(amountParagraph);
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                } finally {
                    stationTotal = stationTotal + Double.valueOf(kwh);
                }
            };
            total = total + stationTotal;

        };

        Paragraph totalParagraph = new Paragraph();
        totalParagraph.setFont(font);

        totalParagraph.add( "Total: "+String.format("%.2f", total) + "kwh");
        totalParagraph.setSpacingBefore(8f);
        totalParagraph.add(new Chunk("\n"));
        try {
            document.add(totalParagraph);
        } catch (DocumentException e) {
            throw  new RuntimeException(e);
        }

        document.close();
    }


}
