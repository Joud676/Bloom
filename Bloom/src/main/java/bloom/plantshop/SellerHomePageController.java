package bloom.plantshop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SellerHomePageController {

    @FXML
    private Button Home_button;

    @FXML
    private Button addPlant_Button;

    @FXML
    private Button back_Button;

    @FXML
    private Button cart_button;

    @FXML
    private Pane main_par;

    @FXML
    private Button setting_button;

    @FXML
    private Button viewPlant_Button;

    @FXML
    void addPlant(ActionEvent event) {
        try {
            // Load the AddPlant.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPlant.fxml"));
            Pane addPlantPane = loader.load();
            
            // Get the current stage and set the new scene
            Stage stage = (Stage) addPlant_Button.getScene().getWindow();
            Scene scene = new Scene(addPlantPane);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    @FXML
    void onClick_button(ActionEvent event){
    	
    }
    

    @FXML
    void viewPlant(ActionEvent event) {
        try {
            // Load the BrowsePlant.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BrowsePlants.fxml"));
            Pane browsePlantPane = loader.load();
            
            // Get the current stage and set the new scene
            Stage stage = (Stage) viewPlant_Button.getScene().getWindow();
            Scene scene = new Scene(browsePlantPane);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}
