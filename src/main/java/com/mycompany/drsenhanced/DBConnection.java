package com.mycompany.drsenhanced;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/drs";
    private static final String USER = "root";      // your MySQL username
    private static final String PASSWORD = "root"; // your actual MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
