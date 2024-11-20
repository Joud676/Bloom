package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    
    public static Connection connectDB() {
        
        final String DB_URL = "jdbc:mysql://localhost:3306/bloom";
        final String DB_USER = "root";
        final String DB_PASSWORD = "100398";
        
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection connect = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
