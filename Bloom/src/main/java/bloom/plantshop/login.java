package bloom.plantshop;

import java.sql.*;

public class login {
	
    
    private final String DB_URL = "jdbc:mysql://localhost:3306/bloom";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Admin@1234";
    
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
    
    
