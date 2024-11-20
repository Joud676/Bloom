package customer;

import util.Navigation;
import util.User;
import util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomerProfileController {

    @FXML
    private ImageView back;

    @FXML
    private Text plantNum;

    @FXML
    private Text userName;

    @FXML
    private Button accountButton;

    @FXML
    private Button passwordButton;

    @FXML
    private Button purchaseButton;

    @FXML
    private Button logoutButton;

    @FXML
    private ImageView profileIcon;

    @FXML
    private ImageView passwordIcon;

    @FXML
    private ImageView cartIcon;

    public void initialize() {
        int customerId = User.getCustomerId();
        loadCustomerDetails(customerId);
        loadPlantCount(customerId);
    }

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.connectDB();
    }

    // Method to retrieve seller details (store name, seller name)
    private void loadCustomerDetails(int customerId) {
        String sellerQuery = "SELECT customerName FROM customer WHERE customerId = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sellerQuery)) {

            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString("customerName");
                userName.setText("@" + username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadPlantCount(int customerId) {
        String plantCountQuery = "SELECT COUNT(*) AS plantCount FROM plantcollection WHERE customerId = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(plantCountQuery)) {

            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int plantCount = resultSet.getInt("plantCount");
                plantNum.setText(String.valueOf(plantCount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void back() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Navigation.navigateTo("/customer/CustomerHomePage.fxml", stage);
    }

    @FXML
    void toAccountInfo(ActionEvent event) {
        String newUsername = dialog("Change your Username", "Change your Username", "Enter your New Username:");

        if (newUsername == null) {
            return; // User pressed cancel, no action needed
        } else if (newUsername.trim().isEmpty()) {
            showAlert("Error", "Username Required", "Please enter a valid new username.", Alert.AlertType.ERROR);
            return; // Show alert and stop further execution
        }

        String updateUsername = "UPDATE customer SET customerName = ? WHERE customerId = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(updateUsername)) {

            statement.setString(1, newUsername);
            statement.setInt(2, User.getCustomerId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert("Success", "Username Updated", "Your username has been successfully updated.", Alert.AlertType.INFORMATION);
                userName.setText("@" + newUsername);
            } else {
                showAlert("Error", "Update Failed", "There was an issue updating your username. Please try again.", Alert.AlertType.ERROR);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "An error occurred while updating your username.", "Please check your connection or try again later.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void toChangePassword(ActionEvent event) {
        String newPassword = dialog("Change your Password", "Change your Password", "Enter your New Password:");

        if (newPassword == null) {
            return; // User pressed cancel, no action needed
        } else if (newPassword.trim().isEmpty()) {
            showAlert("Error", "Password Required", "Please enter a valid new password.", Alert.AlertType.ERROR);
            return; // Show alert and stop further execution
        }

        if (!isValidPassword(newPassword)) {
            showAlert("Error", "Validation Error",
                    "Password must be at least 8 characters long and contain a mix of upper and lower case letters, numbers, and special characters.", Alert.AlertType.ERROR);
            return; // Show alert and stop further execution
        }

        String updatePassword = "UPDATE customer SET password = ? WHERE customerId = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(updatePassword)) {

            statement.setString(1, newPassword);
            statement.setInt(2, User.getCustomerId());
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                showAlert("Success", "Password Updated", "Your password has been successfully updated.", Alert.AlertType.INFORMATION);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "An error occurred while updating your password.", "Please check your connection or try again later.", Alert.AlertType.ERROR);
        }
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
        return Pattern.matches(passwordRegex, password);
    }

    private void showAlert(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    String dialog(String title, String header, String content) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);

        dialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/styles/dialog.css").toExternalForm()
        );
        dialog.getDialogPane().setGraphic(null);

        AtomicReference<String> newPassword = new AtomicReference<>(null);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.initStyle(StageStyle.UNDECORATED);
        dialog.showAndWait().ifPresent(response -> {
            newPassword.set(response);
        });

        return newPassword.get();
    }

    @FXML
    private void toPurchaseHistory() {
        // Logic to navigate to the purchase history screen
    }

    @FXML
    private void logout() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Navigation.navigateTo("/operations/StartingPage.fxml", stage);
    }
}
