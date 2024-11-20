package operations;

import customer.CustomerHomePageController;
import util.User;
import util.DatabaseConnection;
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

    @FXML
    private Button back2signup;

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
            Parent signupRoot = FXMLLoader.load(getClass().getResource("/operations/signup.fxml"));

            Stage stage = (Stage) back2signup.getScene().getWindow();
            stage.setScene(new Scene(signupRoot));
            stage.setTitle("Signup");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the signup interface.");
        }
    }

    private String authenticateUser(String username, String password) {
        String querySeller = "SELECT sellerId FROM seller WHERE sellerName = ? AND password = ?";
        String queryCustomer = "SELECT customerId FROM customer WHERE customerName = ? AND password = ?";

        try (Connection connection = DatabaseConnection.connectDB(); PreparedStatement statementSeller = connection.prepareStatement(querySeller); PreparedStatement statementCustomer = connection.prepareStatement(queryCustomer)) {

            // Check in seller table
            statementSeller.setString(1, username);
            statementSeller.setString(2, password);
            ResultSet resultSetSeller = statementSeller.executeQuery();

            if (resultSetSeller.next()) {
                int sellerId = resultSetSeller.getInt("sellerId");
                User.setSellerId(sellerId); // Store the sellerId in the UserId class
                return "seller";
            }

            // Check in customer table
            statementCustomer.setString(1, username);
            statementCustomer.setString(2, password);
            ResultSet resultSetCustomer = statementCustomer.executeQuery();

            if (resultSetCustomer.next()) {
                int customerId = resultSetCustomer.getInt("customerId");
                User.setCustomerId(customerId); // Store the customerId in the UserId class
                return "customer";
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
            FXMLLoader loader;

            if ("seller".equals(userType)) {
                root = FXMLLoader.load(getClass().getResource("/seller/SellerHomePage.fxml"));
            } else {
                loader = new FXMLLoader(getClass().getResource("/customer/CustomerHomePage.fxml"));
                root = loader.load();

                // Get the controller for the CustomerHomePage
                CustomerHomePageController controller = loader.getController();

                // Pass the customerId to the controller
                int customerId = User.getCustomerId(); // Assuming UserId.getCustomerId() retrieves the ID
                controller.setCustomerId(customerId);
                //   System.out.println("Customer ID: " + customerId);
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

    @FXML
    public void exit() {
        System.exit(0);

    }
}
