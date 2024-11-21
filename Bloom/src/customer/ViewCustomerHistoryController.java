package customer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.sql.*;
import util.*;

public class ViewCustomerHistoryController {

    @FXML
    private VBox plantContainer;

    @FXML
    private ImageView back;

    @FXML
    private ScrollPane scrollPane;

    Image plantImage;

    Label orderIdLabel;
    Label orderDateLabel;
    Label quantityLabel;
    Label priceLabel;
    Label sellerNameLabel;

    public void initialize() {
        fetchDataAndDisplay();
    }

    public void fetchDataAndDisplay() {
        plantContainer.getChildren().clear();

        String orderQuery = "SELECT o.orderId, o.purchaseDate, o.quantity, o.totalPrice, o.plantId, p.image, s.sellerName "
                + "FROM `order` o "
                + "JOIN plant p ON o.plantId = p.plantId "
                + "JOIN seller s ON p.sellerId = s.sellerId "
                + "WHERE o.customerId = ?";

        try (Connection connection = DatabaseConnection.connectDB(); PreparedStatement preparedStatement = connection.prepareStatement(orderQuery)) {

            preparedStatement.setInt(1, User.getCustomerId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int orderId = resultSet.getInt("orderId");
                    Date orderDate = resultSet.getDate("purchaseDate");
                    int plantId = resultSet.getInt("plantId");
                    int quantity = resultSet.getInt("quantity");
                    double price = resultSet.getDouble("totalPrice");
                    byte[] plantImageBytes = resultSet.getBytes("image");
                    String sellerName = resultSet.getString("sellerName");

                    plantImage = new Image(new ByteArrayInputStream(plantImageBytes));

                    createOrderItem(orderId, plantId, orderDate, quantity, price, plantImage, sellerName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createOrderItem(int orderId, int plantId, Date orderDate, int quantity, double price, Image plantImage, String sellerName) {
        orderIdLabel = new Label("Order ID: " + orderId);
        orderDateLabel = new Label("Order Date: " + orderDate.toString());
        quantityLabel = new Label("Quantity: " + quantity);
        priceLabel = new Label("Total Price: " + price);
        sellerNameLabel = new Label("Seller: " + sellerName);

        ImageView plantImageView = new ImageView(plantImage);
        plantImageView.setFitHeight(60);
        plantImageView.setFitWidth(60);

        VBox orderDetailsBox = new VBox(5);  // Vertical spacing between labels
        orderDetailsBox.getChildren().addAll(orderIdLabel, orderDateLabel, sellerNameLabel, quantityLabel, priceLabel);

        HBox orderBox = new HBox(20);
        orderBox.getChildren().addAll(plantImageView, orderDetailsBox);

        orderBox.setStyle("-fx-padding: 10px; -fx-border-color: #ccc; -fx-border-width: 1px;");
        orderDetailsBox.setStyle("-fx-alignment: top-left;");

        plantContainer.getChildren().add(orderBox);
    }

    @FXML
    void back() {
        Stage stage = (Stage) back.getScene().getWindow();
        Navigation.navigateTo("/customer/CustomerProfile.fxml", stage);
    }
}
