package customer;

import util.User;
import util.DatabaseConnection;
import util.Plant;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

public class MarketCardController implements Initializable {

    @FXML
    private Button plantAdd;

    @FXML
    private AnchorPane plantCard;

    @FXML
    private ImageView plantImage;

    @FXML
    private Label plantName;

    @FXML
    private Label plantPrice;

    @FXML
    private Spinner<Integer> plantQuan;

    @FXML
    private Label plantInfo;

    private Image image;
    private Plant plant;

    public void setPlantData(Plant plant) {
        this.plant = plant;
        plantName.setText(plant.getName());
        plantPrice.setText(String.valueOf(plant.getPrice()));
        plantInfo.setText(plant.getCharacteristic());

        byte[] imageBytes = plant.getImage();
        ByteArrayInputStream bytes;

        if (imageBytes != null && imageBytes.length > 0) {
            bytes = new ByteArrayInputStream(imageBytes);
        } else {
            bytes = new ByteArrayInputStream(getDefaultImageBytes());
        }

        image = new Image(bytes, 80, 90, true, true);
        plantImage.setImage(image);
        plantQuan.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, plant.getQuantity(), 0));
    }

    private byte[] getDefaultImageBytes() {
        return new byte[]{};
    }

    @FXML
    void addToCart() {
        if (plantQuan.getValue() > 0) {
            Connection con = DatabaseConnection.connectDB();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Plant Added");
            alert.setContentText("Plant is added to cart!");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/styles/dialog.css").toExternalForm());
            alert.show();
            try {
                PreparedStatement ps = con.prepareStatement("INSERT INTO cart (customerId, plantId, quantity) VALUES (?, ?, ?);");
                ps.setInt(1, User.getCustomerId());
                ps.setInt(2, plant.getPlantId());
                ps.setInt(3, plantQuan.getValue());
                ps.executeUpdate();

            } catch (SQLException ex) {
                if (ex.getMessage().contains("cart.PRIMARY")) {
                    try {
                        PreparedStatement ps = con.prepareStatement("UPDATE cart SET quantity = quantity + ? WHERE customerId = ? AND plantId = ? ;");
                        ps.setInt(1, plantQuan.getValue());
                        ps.setInt(2, User.getCustomerId());
                        ps.setInt(3, plant.getPlantId());
                        ps.executeUpdate();
                    } catch (SQLException ex1) {
                        Logger.getLogger(MarketCardController.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } else {
                    ex.printStackTrace();
                }
            }

        }
    }

    @FXML
    void imageClicked(MouseEvent event) {
        String name = plant.getName();
        double price = plant.getPrice();
        String characteristics = plant.getCharacteristic();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Plant Information");
        alert.setHeaderText(name);
        alert.setContentText(String.format("Price: $%.2f\nCharacteristics: %s", price, characteristics));
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/styles/dialog.css").toExternalForm());
        alert.showAndWait();

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

}
