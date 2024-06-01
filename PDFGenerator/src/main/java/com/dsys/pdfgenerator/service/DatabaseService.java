package com.dsys.pdfgenerator.service;

import com.dsys.pdfgenerator.model.Customer;

import java.sql.*;

public class DatabaseService {
    private static Connection connect() throws SQLException {
        String connectionString="jdbc:postgresql://localhost:30001/customerdb";
        return DriverManager.getConnection(connectionString, "postgres", "postgres");
    }



    public static Customer getCustomer(String id) throws SQLException {
        Customer customer;
        try ( Connection conn = connect() ) {
            String query = "SELECT id, first_name, last_name FROM customer WHERE id = " + id + ";";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            if(!resultSet.next()) {
                throw new SQLException("Customer not found in DB");
            } else {
                 customer = new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")
                );
            }
            return customer;
        }
    }
}
