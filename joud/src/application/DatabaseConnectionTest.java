package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionTest {

    // Database connection details

	

    private static final String DB_URL = "jdbc:mysql://localhost:3306/bloom"; 
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "Rr120178593!";

    public static void main(String[] args) {
        // Attempt to connect to the database
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}

