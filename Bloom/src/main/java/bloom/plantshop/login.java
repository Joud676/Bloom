package application;

import java.sql.*;

public class login {

    String username;
    String password;

    public boolean login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = database.connectDB(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); //true if a record is found

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
