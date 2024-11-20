package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class SellerHomePageController {

    @FXML
    private ImageView home;
    
    
    @FXML
    private ImageView profileImg;
    
    @FXML
    private Button addPlant_Button;

    @FXML
    private Button setting_button;

    @FXML
    private Button viewPlant_Button;

    @FXML
    private ImageView profile;

    
    @FXML
    void addPlant(ActionEvent event) {
        Stage stage = (Stage) addPlant_Button.getScene().getWindow();
        Navigation.navigateTo("AddPlant.fxml", stage);
    }
    
    @FXML
    void toProfile() {
    	 Stage stage = (Stage) addPlant_Button.getScene().getWindow();
         Navigation.navigateTo("SellerProfile.fxml", stage);
    }
    @FXML
    void purchase(ActionEvent event){
    	
    }
    

    @FXML
    void viewPlant(ActionEvent event) {
        Stage stage = (Stage) viewPlant_Button.getScene().getWindow();
        Navigation.navigateTo("BrowsePlants.fxml", stage);
    }
}
