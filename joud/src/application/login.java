package application;

import java.sql.*;

public class login {
	
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bloom"; 
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "Rr120178593!";
    
    String username;
    String password;
  
   
    
    public boolean login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?"; 

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);
            
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); //true if a record is found

        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }}
    
    
