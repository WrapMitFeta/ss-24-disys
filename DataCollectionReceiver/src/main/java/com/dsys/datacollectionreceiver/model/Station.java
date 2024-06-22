package com.dsys.datacollectionreceiver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
public class Station {
    @Getter @Setter
    private String id;

    @Getter @Setter
    private String kwh;
}
