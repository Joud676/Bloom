package plant;

import plant.PlantDetailsController;
import util.Plant;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Fertilization_Controller {

    @FXML
    private Button Fertilization_Button;

    @FXML
    private TextField Plant2_Name;

    @FXML
    private Label PlantName;

    @FXML
    private Text Result_Of_Fertilizastion;

    @FXML
    private Button back_Button;

    @FXML
    private ImageView plantImage;

    private Plant selectedPlant; // The plant passed from the previous page
    @FXML
    private AnchorPane mainPan;
    @FXML
    private ImageView plantImage1;
    @FXML
    private Label PlantName1;

    
    public void setFertilizationData(Plant selectedPlant) {
        this.selectedPlant = selectedPlant;

        // Display the selected plant's data
        PlantName.setText(selectedPlant.getName());
        if (selectedPlant.getImage() != null && selectedPlant.getImage().length > 0) {
            byte[] imageBytes = selectedPlant.getImage();
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            Image image = new Image(bis);
            plantImage.setImage(image);
        } else {
            System.out.println("No image data available or image data is empty.");
        }
    }

    @FXML
    void checkFertilization(ActionEvent event) {
        String plant2Name = Plant2_Name.getText().trim();

        // Validate the input
        if (plant2Name.isEmpty()) {
            
            Result_Of_Fertilizastion.setText("Please enter a plant name.");
            selectedPlant.getFertilization().contains(plant2Name);
            return;
        }

        // Check if the plant name exists in the selected plant's fertilization list
        if (selectedPlant.getFertilization().contains(plant2Name)) {
            Result_Of_Fertilizastion.setText(
                "The plant " + selectedPlant.getName() + " can be fertilized with " + plant2Name + "."
            );
        } else {
            Result_Of_Fertilizastion.setText(
                "The plant " + selectedPlant.getName() + " cannot be fertilized with " + plant2Name + "."
            );
        }
    }

    @FXML void onClick_button(ActionEvent event) {
    	try { FXMLLoader loader = new FXMLLoader(getClass().getResource("/plant/PlantDetails.fxml"));
    	Parent root = loader.load(); 
    	PlantDetailsController plantDetailsController = loader.getController();
    	plantDetailsController.setPlantData(selectedPlant); 
    	Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
    	stage.setScene(new Scene(root)); stage.show(); } catch (IOException e) {
}
    	}
}
