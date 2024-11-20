package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AddPlantController {

    @FXML
    private TextField nameText;
    @FXML
    private TextArea charText;
    @FXML
    private TextArea careText;
    @FXML
    private TextField priceText;
    @FXML
    private TextField quantityText;
    @FXML
    private ImageView importedImage;
    @FXML
    private Button importButton;
    @FXML
    private TextArea Fertilization_Text;


    @FXML 
    private Button back;
    private File selectedImageFile;

    private FileChooser fileChooser;

    // Method to handle importing the image
    @FXML
    public void importImage() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        selectedImageFile = fileChooser.showOpenDialog(new Stage());

        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString(), 100, 1500, true, true);
            importedImage.setPreserveRatio(true);
            importedImage.setImage(image);
        } else {
            showAlert(AlertType.WARNING, "No Image Selected", "Please select an image to import.");
        }
    }

    @FXML
    public void addPlant() throws IOException {
        String plantName = nameText.getText();
        String characteristics = charText.getText();
        String careInfo = careText.getText();
        String fertilizationInfo = Fertilization_Text.getText(); // Get fertilization text

        double price;
        int quantity;

        // Parse price and quantity with exception handling
        try {
            price = Double.parseDouble(priceText.getText());
            quantity = Integer.parseInt(quantityText.getText());
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Invalid Input", "Please enter valid numeric values for price and quantity.");
            return;
        }

        System.out.println("Inserting plant: " + plantName + ", " + characteristics + ", " + careInfo + ", " + fertilizationInfo + ", " + price + ", " + quantity + ", " + UserId.getSellerId());

        try (Connection connection = database.connectDB();
             PreparedStatement statement = connection.prepareStatement(
                 "INSERT INTO plant (plantName, characteristics, careInfo, fertilization_list, price, quantity, sellerId, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

            statement.setString(1, plantName);
            statement.setString(2, characteristics);
            statement.setString(3, careInfo);
            statement.setString(4, fertilizationInfo); // Set fertilization info
            statement.setDouble(5, price);
            statement.setInt(6, quantity);
            statement.setInt(7, UserId.getSellerId());

            // Handle image input
            if (selectedImageFile != null && selectedImageFile.exists()) {
                try (FileInputStream fis = new FileInputStream(selectedImageFile)) {
                    statement.setBinaryStream(8, fis, (int) selectedImageFile.length());
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        showAlert(AlertType.INFORMATION, "Success", "Plant Added Successfully!");
                    } else {
                        showAlert(AlertType.ERROR, "Insert Failed", "Failed to add plant to the database.");
                    }
                } catch (IOException e) {
                    showAlert(AlertType.ERROR, "IO Error", "Error reading the image file: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                statement.setNull(8, java.sql.Types.BLOB);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert(AlertType.INFORMATION, "Success", "Plant Added Successfully!");
                } else {
                    showAlert(AlertType.ERROR, "Insert Failed", "Failed to add plant to the database.");
                }
            }

        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", "Error Adding Plant: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void exit() {
        System.exit(0);
    }


    @FXML
    void onClick_button() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SellerHomePage.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) back.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
}
	    }
	 

