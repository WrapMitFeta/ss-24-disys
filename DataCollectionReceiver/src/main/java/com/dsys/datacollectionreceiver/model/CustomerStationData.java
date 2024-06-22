package com.dsys.datacollectionreceiver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
public class CustomerStationData
{
    @Getter @Setter
    private String customer_id;

    @Getter @Setter
    private ArrayList<Station> stations;


}
