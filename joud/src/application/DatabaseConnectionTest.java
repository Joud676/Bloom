package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionTest {

   


    public static void main(String[] args) {
    	 // Database connection details
          final String DB_URL = "jdbc:mysql://localhost:3306/bloom"; 
          final String DB_USER = "root"; 
          final String DB_PASSWORD = "Rr120178593!";
        // Attempt to connect to the database
        try (Connection connection = database.connectDB()){
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}

