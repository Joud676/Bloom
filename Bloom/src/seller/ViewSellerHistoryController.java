package seller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.sql.*;
import util.*;

public class ViewSellerHistoryController {

    @FXML
    private VBox plantContainer; 
    
    @FXML
    private Button searchButton; 
    
    @FXML
    private ImageView back; 
    
    @FXML
    private ScrollPane scrollPane; 
    
    Label orderIdLabel, customerNameLabel, plantNameLabel, orderDateLabel;
    
    @FXML
    public void initialize() {
        fetchDataAndDisplay();
    }
   
    public void fetchDataAndDisplay() {
        plantContainer.getChildren().clear();

        String orderQuery = "SELECT orderId, customerId, plantId, purchaseDate FROM `order` WHERE sellerId = ?";

        try (Connection connection = DatabaseConnection.connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(orderQuery)) {

            preparedStatement.setInt(1, User.getSellerId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

            	while (resultSet.next()) {
                    int orderId = resultSet.getInt("orderId");
                    int customerId = resultSet.getInt("customerId");
                    int plantId = resultSet.getInt("plantId");
                    Date orderDate = resultSet.getDate("purchaseDate");

                    String customerName = getCustomerNameById(customerId, connection);
                    String plantName = getPlantNameById(plantId, connection);

                    createOrderItem(orderId, customerName, plantName, orderDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getCustomerNameById(int customerId, Connection connection) {
        String customerQuery = "SELECT customerName FROM customer WHERE customerId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(customerQuery)) {
            preparedStatement.setInt(1, customerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("customerName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Unknown Customer"; // Default if customer not found
    }

    private String getPlantNameById(int plantId, Connection connection) {
        String plantQuery = "SELECT plantName FROM plant WHERE plantId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(plantQuery)) {
            preparedStatement.setInt(1, plantId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("plantName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Unknown Plant"; // Default if plant not found
    }

    private void createOrderItem(int orderId, String customerName, String plantName, Date orderDate) {
        // Create labels for each order's details
         orderIdLabel = new Label("Order ID: " + orderId);
         customerNameLabel = new Label("Customer Name: " + customerName);  // Use customer name
         plantNameLabel = new Label("Plant Name: " + plantName);  // Display plant name
         orderDateLabel = new Label("Order Date: " + orderDate.toString());

        // Create a VBox to stack the labels vertically (each label on a new line)
        VBox orderDetailsBox = new VBox(5);  // Vertical spacing between labels
        orderDetailsBox.getChildren().addAll(orderIdLabel, customerNameLabel, plantNameLabel, orderDateLabel);

        HBox orderBox = new HBox(20);  // Spacing between the VBox 
        orderBox.getChildren().add(orderDetailsBox);

        orderBox.setStyle("-fx-padding: 10px; -fx-border-color: #ccc; -fx-border-width: 1px; -fx-alignment: top-left;");
        orderDetailsBox.setStyle("-fx-alignment: top-left;");

        plantContainer.getChildren().add(orderBox);
    }

    @FXML
    void back() {
    	Stage stage = (Stage) back.getScene().getWindow();
        Navigation.navigateTo("/seller/SellerProfile.fxml", stage);    	
    }
}
