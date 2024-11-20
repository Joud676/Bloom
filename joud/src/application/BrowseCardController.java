package application;

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

public class BrowseCardController implements Initializable {

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
    }

    private byte[] getDefaultImageBytes() {
        return new byte[]{};
    }

    private int qty;

    @FXML
    void addToCart() {
        if (plantQuan.getValue() > 0) {
            Connection con = con = database.connectDB();
            try {
                PreparedStatement ps = con.prepareStatement("INSERT INTO cart (customerId, plantId, quantity) VALUES (?, ?, ?);");
                ps.setInt(1, UserId.getCustomerId());
                ps.setInt(2, plant.getPlantId());
                ps.setInt(3, plantQuan.getValue());
                ps.executeUpdate();
            
            } catch (SQLException ex) {
                if (ex.getMessage().contains("cart.PRIMARY")) {
                    try {
                        PreparedStatement ps = con.prepareStatement("UPDATE cart SET quantity = quantity + ? WHERE customerId = ? AND plantId = ? ;");
                        ps.setInt(1, plantQuan.getValue());
                        ps.setInt(2, UserId.getCustomerId());
                        ps.setInt(3, plant.getPlantId());
                        ps.executeUpdate();
                    } catch (SQLException ex1) {
                        Logger.getLogger(BrowseCardController.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }else{
                    ex.printStackTrace();
                }
            }

        }
    }

    @FXML
    void imageClicked() {

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        plantQuan.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));

    }

}
