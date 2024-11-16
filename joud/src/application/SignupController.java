package application;

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
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    @FXML
    private Button back2login;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306"; 
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "100398";
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
                authenticateUser(username,password);
                loadSellerHomePage();
            } else if ("Customer".equals(accountTypeSelection)) {
                saveCustomerToDatabase(username, email, password);
                authenticateUser(username,password);
                loadCustomerHomePage();
            }
        }
    }

    @FXML
     void login() {
        try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("login.fxml"));

            Stage stage = (Stage) back2login.getScene().getWindow();
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
            Stage stage = (Stage) signup.getScene().getWindow(); 
            stage.setScene(new Scene(root));
            stage.setTitle("Seller Home Page");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Loading Failed", "Error loading Seller Home Page: " + e.getMessage());
        }
    }
    private String authenticateUser(String username, String password) {
        String querySeller = "SELECT sellerId FROM seller WHERE sellerName = ? AND password = ?";
        String queryCustomer = "SELECT customerId FROM customer WHERE customerName = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statementSeller = connection.prepareStatement(querySeller);
             PreparedStatement statementCustomer = connection.prepareStatement(queryCustomer)) {

            // Check in seller table
            statementSeller.setString(1, username);
            statementSeller.setString(2, password);
            ResultSet resultSetSeller = statementSeller.executeQuery();

            if (resultSetSeller.next()) {
                int sellerId = resultSetSeller.getInt("sellerId");
                UserId.setSellerId(sellerId); // Store the sellerId in the UserId class
                return "seller"; 
            }

            // Check in customer table
            statementCustomer.setString(1, username);
            statementCustomer.setString(2, password);
            ResultSet resultSetCustomer = statementCustomer.executeQuery();

            if (resultSetCustomer.next()) {
                int customerId = resultSetCustomer.getInt("customerId");
                UserId.setCustomerId(customerId); // Store the customerId in the UserId class
                return "customer"; 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
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

            String querySellerIdSQL = "SELECT sellerId FROM seller WHERE sellerName = ? AND email = ?";
            try (PreparedStatement queryStatement = connection.prepareStatement(querySellerIdSQL)) {
                queryStatement.setString(1, username);
                queryStatement.setString(2, email);
                ResultSet resultSet = queryStatement.executeQuery();
                if (resultSet.next()) {
                    int id = resultSet.getInt("sellerId");
                    UserId.setSellerId(id); 
                }
            }

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

            // Execute the insert operation
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                // Fetch the customerId after inserting
                String queryCustomerIdSQL = "SELECT customerId FROM customer WHERE customerName = ? AND email = ?";
                try (PreparedStatement queryStatement = connection.prepareStatement(queryCustomerIdSQL)) {
                    queryStatement.setString(1, username);
                    queryStatement.setString(2, email);
                    ResultSet resultSet = queryStatement.executeQuery();
                    if (resultSet.next()) {
                        int id = resultSet.getInt("customerId");
                        UserId.setCustomerId(id); // Store the customerId in the UserId class
                        System.out.println("Retrieved Customer ID: " + id); // Log the retrieved ID
                    } else {
                        System.out.println("No customer found after insert.");
                    }
                }
                showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Customer account created successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "Failed to create customer account.");
            }
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
    @FXML
    public void exit() {
        System.exit(0);

    }
}

