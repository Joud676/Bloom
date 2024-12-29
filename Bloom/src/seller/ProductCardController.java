package seller;

import util.Plant;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ProductCardController implements Initializable {

    @FXML
    private Button info;

    @FXML
    private AnchorPane plantCard;

    @FXML
    private ImageView plantImage;
    
    @FXML
    private ImageView soldout;

    @FXML
    private Label plantName;

    @FXML
    private Label plantPrice;
  
    private Image soldoutImg = new Image("images/soldout.png");

    private Image image;
    private Plant plant;
    public void setPlantData(Plant plant) {
        this.plant = plant;
        plantName.setText(plant.getName());
        plantPrice.setText(String.valueOf(plant.getPrice()));

        byte[] imageBytes = plant.getImage();
        ByteArrayInputStream bytes;

        if (imageBytes != null && imageBytes.length > 0) {
            bytes = new ByteArrayInputStream(imageBytes);
        } else {
            bytes = new ByteArrayInputStream(getDefaultImageBytes());
        }

        image = new Image(bytes, 80, 90, true, true);
        plantImage.setImage(image);
        if (plant.getQuantity()==0) {
        soldout.setImage(soldoutImg);
        }
    }

    private byte[] getDefaultImageBytes() {
        return new byte[]{};
    }

    @FXML
    void displayInfo(ActionEvent event) {
    	imageClicked();
    }
    @FXML
    void imageClicked() {
        String name = plant.getName();
        double price = plant.getPrice();
        String characteristics = plant.getCharacteristic();
        int quantity = plant.getQuantity();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Plant Information");
        alert.setHeaderText(name);
        alert.setContentText(String.format("Price: $%.2f\nQuantity: %d\nCharacteristics: %s", price, quantity, characteristics));
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/styles/dialog.css").toExternalForm());
        alert.showAndWait();
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

}