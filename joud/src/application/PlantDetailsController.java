package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import java.awt.TextField;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javafx.event.ActionEvent;

public class PlantDetailsController {
	@FXML
    private Button Fertilization_Button;

    @FXML
    private Label PlantName;

    @FXML
    private Button back_Button;

    @FXML
    private Text careText;

    @FXML
    private Text infoText;

    @FXML
    private AnchorPane mainPan;

    @FXML
    private ImageView plantImage;

    // Method to set and display plant data
    public void setPlantData(Plant plant) {
        PlantName.setText(plant.getName()); // Example for care info
       infoText.setText(plant.getCharacteristic()); 
       careText.setText(plant.getCareInfo());

        // Set the image
        byte[] imageBytes = plant.getImage();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        Image image = new Image(bis);
        plantImage.setImage(image);
    }

    @FXML
    void checkFertilization(ActionEvent event) {
        // Logic for checking fertilization
    }

    @FXML
    void onClick_button(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerHomePage.fxml"));
            Parent root = loader.load();

            // Get the CustomerHomePageController
            CustomerHomePageController controller = loader.getController();

            // Set the customer ID and call loadPlantCards again
            int customerId = UserId.getCustomerId();
            System.out.print(customerId + " in the plant details");
            controller.setCustomerId(customerId);

            // Get the current stage and set the scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
