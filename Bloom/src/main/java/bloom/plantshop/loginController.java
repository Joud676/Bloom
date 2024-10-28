package bloom.plantshop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginController {

    @FXML
    private Button Login;

    @FXML
    private Text signupLabel;
    
    @FXML
    private Text passwordLabel;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Text usernameLabel;

    @FXML
    private TextField usernameText;

    private final String DB_URL = "jdbc:mysql://localhost:3306/bloom";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Admin@1234";

    @FXML
    void login(ActionEvent event) {
        String username = usernameText.getText();
        String password = passwordText.getText();

        String userType = authenticateUser(username, password);

        if (userType != null) {
            navigateToHomePage(userType);
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }
    
    @FXML
    void signup() {
        try {
            Parent signupRoot = FXMLLoader.load(getClass().getResource("signup.fxml"));

            Stage stage = (Stage) usernameText.getScene().getWindow(); 
            stage.setScene(new Scene(signupRoot));
            stage.setTitle("Signup"); 
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the signup interface.");
        }
    }

    private String authenticateUser(String username, String password) {
        String querySeller = "SELECT * FROM seller WHERE sellerName = ? AND password = ?";
        String queryCustomer = "SELECT * FROM customer WHERE customerName = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statementSeller = connection.prepareStatement(querySeller);
             PreparedStatement statementCustomer = connection.prepareStatement(queryCustomer)) {

            // Check in seller table
            statementSeller.setString(1, username);
            statementSeller.setString(2, password);
            ResultSet resultSetSeller = statementSeller.executeQuery();

            if (resultSetSeller.next()) {
                return "seller"; // User is a seller
            }

            // Check in customer table
            statementCustomer.setString(1, username);
            statementCustomer.setString(2, password);
            ResultSet resultSetCustomer = statementCustomer.executeQuery();

            if (resultSetCustomer.next()) {
                return "customer"; // User is a customer
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // User not found
    }

   
    
    private void navigateToHomePage(String userType) {
        try {
            Stage stage = (Stage) Login.getScene().getWindow();
            Parent root;
            if ("seller".equals(userType)) {
                root = FXMLLoader.load(getClass().getResource("SellerHomePage.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("CustomerHomePage.fxml"));
            }
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
   
}
