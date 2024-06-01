package com.dsys.pdfgenerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Customer {
    @Getter @Setter
    private int customer_id;
    @Getter @Setter
    private String first_name;
    @Getter @Setter
    private String last_name;
}
