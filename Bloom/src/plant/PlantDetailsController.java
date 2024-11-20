package plant;

import customer.CustomerHomePageController;
import util.User;
import util.Plant;
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
import util.Navigation;

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
    private Plant currentPlant; // Hold the current plant data 
    public void setPlantData(Plant plant) { 
    	this.currentPlant = plant; // Save the plant object 
    	PlantName.setText(plant.getName());
    	infoText.setText(plant.getCharacteristic()); 
    	careText.setText(plant.getCareInfo()); 
    	byte[] imageBytes = plant.getImage();
    	ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes); 
    	Image image = new Image(bis); plantImage.setImage(image);
    	} 
    @FXML 
    void move_to_Fertilization(ActionEvent event) { 
    Stage stage = (Stage) infoText.getScene().getWindow();
        Navigation.navigateTo("/plant/FertilizationPage.fxml", stage);
    
    } 
    
    
    @FXML void onClick_button(ActionEvent event) { 
    Stage stage = (Stage) infoText.getScene().getWindow();
        Navigation.navigateTo("/customer/CustomerHomePage.fxml", stage);
    
    }
    	
    
}
