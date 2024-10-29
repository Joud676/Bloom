package bloom.plantshop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;

public class SellerHomePageController {

    @FXML
    private Text welcomeLabel;
    
    @FXML
    private Button addPlantButton;
   
    private String username;

    

    // Method to handle opening the AddPlant.fxml
    @FXML
    private void openAddPlant(ActionEvent event) {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPlant.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void openViewPlants(ActionEvent event) {
       
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    
}
