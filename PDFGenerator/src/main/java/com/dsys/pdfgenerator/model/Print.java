package com.dsys.pdfgenerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
public class Print {
    @Getter @Setter
    private Customer customer;
    @Getter @Setter
    private ArrayList<StationBillingList> billings;

}
