package bloom.plantshop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Pattern;

public class SignupController {

    @FXML
    private TextField nameText;
    @FXML
    private TextField emailText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private ComboBox<String> accountType;
    @FXML
    private Button signup;
    @FXML
    private Text login;

    // Database connection details
    private final String DB_URL = "jdbc:mysql://localhost:3306/bloom";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Admin@1234";

    // Initialize method to set ComboBox items
    @FXML
    public void initialize() {
        accountType.getItems().addAll("Seller", "Customer");
    }

    @FXML
    public void signup(ActionEvent event) {
        String username = nameText.getText();
        String email = emailText.getText();
        String password = passwordText.getText();
        String accountTypeSelection = accountType.getValue();

        if (validateInputs(username, email, password, accountTypeSelection)) {
            if ("Seller".equals(accountTypeSelection)) {
                // Prompt for store name if the account type is Seller
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Store Name");
                dialog.setHeaderText("Enter your Store Name");
                dialog.setContentText("Store Name:");

                Optional<String> storeNameResult = dialog.showAndWait();
                storeNameResult.ifPresent(storeName -> saveSellerToDatabase(username, email, password, storeName));
                loadSellerHomePage();
            } else if ("Customer".equals(accountTypeSelection)) {
                saveCustomerToDatabase(username, email, password);
                loadCustomerHomePage();
            }
        }
    }

    @FXML
    private void login() {
        try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("login.fxml"));

            Stage stage = (Stage) nameText.getScene().getWindow(); 
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Login"); 
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the login interface.");
        }
    }

    private void loadSellerHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SellerHomePage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) signup.getScene().getWindow(); // Get the current stage
            stage.setScene(new Scene(root));
            stage.setTitle("Seller Home Page");
            stage.show();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Loading Failed", "Error loading Seller Home Page: " + e.getMessage());
        }
    }
    
    private void loadCustomerHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerHomePage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) signup.getScene().getWindow(); // Get the current stage
            stage.setScene(new Scene(root));
            stage.setTitle("Customer Home Page");
            stage.show();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Loading Failed", "Error loading Customer Home Page: " + e.getMessage());
        }
    }
    
    private boolean validateInputs(String username, String email, String password, String accountTypeSelection) {
        // Check if any fields are empty
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || accountTypeSelection == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields are required.");
            return false;
        }

        // Check email format
        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid email format.");
            return false;
        }

        // Check password format
        if (!isValidPassword(password)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", 
                      "Password must be at least 8 characters long and contain a mix of upper and lower case letters, numbers, and special characters.");
            return false;
        }

        return true;
    }

    // Helper method to validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    // Helper method to validate password format
    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
        return Pattern.matches(passwordRegex, password);
    }

    private void saveSellerToDatabase(String username, String email, String password, String storeName) {
        String insertSellerSQL = "INSERT INTO seller (sellerName, email, password, accountType, storeName) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertSellerSQL)) {

            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, "Seller");
            statement.setString(5, storeName);
            statement.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Seller account created successfully!");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Error: " + e.getMessage());
        }
    }

    private void saveCustomerToDatabase(String username, String email, String password) {
        String insertCustomerSQL = "INSERT INTO customer (customerName, email, password, accountType) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertCustomerSQL)) {

            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, "Customer");
            statement.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Customer account created successfully!");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Error: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

