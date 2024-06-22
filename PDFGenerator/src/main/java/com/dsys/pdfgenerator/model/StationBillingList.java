package com.dsys.pdfgenerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.util.ArrayList;

@AllArgsConstructor
public class StationBillingList {
    @Getter @Setter
    private String station_id;

    @Getter @Setter
    private ArrayList<String> kwh;
}
