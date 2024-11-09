package bloom.plantshop;

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
	private Button back;
	private File selectedImageFile;

	private  FileChooser fileChooser;
	// Database connection details
	private final String DB_URL = "jdbc:mysql://localhost:3306/bloom";
	private final String DB_USER = "root";
	private final String DB_PASSWORD = "Admin@1234";

	// Method to handle importing the image
	@FXML
	public void importImage() {
	     fileChooser = new FileChooser();
	    fileChooser.getExtensionFilters().addAll(
	            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
	    selectedImageFile = fileChooser.showOpenDialog(new Stage());

	    if (selectedImageFile != null) {
	        Image image = new Image(selectedImageFile.toURI().toString(), 100,1500, true, true);
	        importedImage.setPreserveRatio(true);
	        importedImage.setImage(image);
	                } else {
	        showAlert(AlertType.WARNING, "No Image Selected", "Please select an image to import.");
	    }
	}

	@FXML
	public void addPlant() throws FileNotFoundException, IOException {
	    String plantName = nameText.getText();
	    String characteristics = charText.getText();
	    String careInfo = careText.getText();

	    double price;
	    int quantity;

	    try {
	        price = Double.parseDouble(priceText.getText());
	        quantity = Integer.parseInt(quantityText.getText());
	    } catch (NumberFormatException e) {
	        showAlert(AlertType.ERROR, "Invalid Input", "Please enter valid numeric values for price and quantity.");
	        return;
	    }

	    System.out.println("Inserting plant: " + plantName + ", " + characteristics + ", " + careInfo + ", " + price + ", " + quantity + ", " + UserId.getSellerId());

	    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	         PreparedStatement statement = connection.prepareStatement(
	                 "INSERT INTO plant (plantName, characteristics, careInfo, price, quantity, sellerId, image) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

	        statement.setString(1, plantName);
	        statement.setString(2, characteristics);
	        statement.setString(3, careInfo);
	        statement.setDouble(4, price);
	        statement.setInt(5, quantity);
	        statement.setInt(6, UserId.getSellerId());
	     // Handle image input
	        if (selectedImageFile != null && selectedImageFile.exists()) {
	            try (FileInputStream fis = new FileInputStream(selectedImageFile)) {
	                statement.setBinaryStream(7, fis, (int) selectedImageFile.length());
	                int rowsAffected = statement.executeUpdate();
	                if (rowsAffected > 0) {
	    	            showAlert(Alert.AlertType.INFORMATION, "Success", "Plant Added Successfully!");
	                } else {
	    	            showAlert(AlertType.ERROR, "Insert Failed", "Failed to add plant to the database.");

	                }
	            } catch (SQLException e) {
	                showAlert(AlertType.ERROR, "SQL Exception", "Error executing SQL statement: " + e.getMessage());
	                e.printStackTrace();
	            }
	        } else {
	            statement.setNull(7, java.sql.Types.BLOB);
	        }

	    } catch (SQLException e) {
	        showAlert(AlertType.ERROR, "Database Error", "Error Adding Plant: " + e.getMessage());
	        e.printStackTrace(); // Log the stack trace for debugging
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