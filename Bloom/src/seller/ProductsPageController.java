package seller;

import customer.CustomerHomePageController;
import util.Navigation;
import util.User;
import util.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductsPageController {

    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;
    @FXML
    private ImageView image4;

    @FXML
    private Label text1;
    @FXML
    private Label text2;
    @FXML
    private Label text3;
    @FXML
    private Label text4;

    @FXML
    private Label price1;
    @FXML
    private Label price2;
    @FXML
    private Label price3;
    @FXML
    private Label price4;

    @FXML
    private Button checkoutButton;

    @FXML
    private Button back;

    private String[] plantNames = new String[4];
    private double[] plantPrices = new double[4];
    private String[] plantCharacteristics = new String[4];

    @FXML
    public void initialize() {
        try {
            loadPlants();
            checkPlantQuantities(); // Check plant quantities after loading plants
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPlants() {
        Connection connection = null;

        try {
            connection = DatabaseConnection.connectDB();

            String sql = "SELECT plantId, plantName, price, characteristics, image, quantity FROM plant WHERE sellerId = ? LIMIT 4";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, User.getSellerId());

            ResultSet resultSet = statement.executeQuery();

            int index = 0;
            while (resultSet.next() && index < 4) {
                int plantId = resultSet.getInt("plantId");
                byte[] imageBytes = resultSet.getBytes("image");
                String plantName = resultSet.getString("plantName");
                double price = resultSet.getDouble("price");
                String characteristics = resultSet.getString("characteristics");

                plantNames[index] = plantName;
                plantPrices[index] = price;
                plantCharacteristics[index] = characteristics;

                switch (index) {
                    case 0:
                        setPlantDetails(imageBytes, plantName, price, image1, text1, price1);
                        break;
                    case 1:
                        setPlantDetails(imageBytes, plantName, price, image2, text2, price2);
                        break;
                    case 2:
                        setPlantDetails(imageBytes, plantName, price, image3, text3, price3);
                        break;
                    case 3:
                        setPlantDetails(imageBytes, plantName, price, image4, text4, price4);
                        break;
                }

                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

    private void setPlantDetails(byte[] buf, String plantName, double price, ImageView imageView, Label plantNameLabel, Label priceLabel) {
        plantNameLabel.setText(plantName != null ? plantName : "Unknown Plant");

        priceLabel.setText(String.format("$%.2f", price));

        if (buf != null) {
            InputStream inputStream = new ByteArrayInputStream(buf);
            Image image = new Image(inputStream);
            imageView.setImage(image);
        } else {
            System.err.println("Image byte array is null for " + plantName);
        }
    }

    private void retrieveInfo(int index) {
        String plantName = plantNames[index];
        double price = plantPrices[index];
        String characteristics = plantCharacteristics[index];

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Plant Information");
        alert.setHeaderText(plantName);
        alert.setContentText("Price: " + String.format("$%.2f", price) + "\nCharacteristics: " + characteristics);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/styles/dialog.css").toExternalForm());
        alert.showAndWait();
    }

    @FXML
    private void imageClicked(MouseEvent event) {
        if (event.getSource() == image1) {
            retrieveInfo(0);
        } else if (event.getSource() == image2) {
            retrieveInfo(1);
        } else if (event.getSource() == image3) {
            retrieveInfo(2);
        } else if (event.getSource() == image4) {
            retrieveInfo(3);
        }
    }

    private void checkPlantQuantities() {
        Connection connection = null;

        try {
            connection = DatabaseConnection.connectDB();

            String sql = "SELECT plantName, quantity FROM plant WHERE sellerId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, User.getSellerId());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String plantName = resultSet.getString("plantName");
                int quantity = resultSet.getInt("quantity");

                if (quantity <= 3) {
                    showLowStockNotification(plantName, quantity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

    private void showLowStockNotification(String plantName, int quantity) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Low Stock Alert");
        alert.setHeaderText("Low Stock for Plant: " + plantName);
        alert.setContentText("The stock for this plant is low.\n Current quantity: " + quantity);
        alert.showAndWait();
    }

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void searchPlant() {

    }

    @FXML
    public void checkOut() {
    }

    @FXML
    public void AddToCart() {
    }

    @FXML
    void onClick_button() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/seller/SellerHomePage.fxml"));
            Parent root = loader.load();

            // Get the CustomerHomePageController
            CustomerHomePageController controller = loader.getController();

            // Set the customer ID and call loadPlantCards again
            int customerId = User.getCustomerId();
            System.out.print(customerId + " in the plant details");
            controller.setCustomerId(customerId);

            Stage currentStage = (Stage) back.getScene().getWindow();

            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

        } catch (Exception e) {
            e.printStackTrace();
        }

// Simplified navigation for fallback
        Stage currentStage = (Stage) back.getScene().getWindow();
        Navigation.navigateTo("/seller/SellerHomePage.fxml", currentStage);

    }
}
