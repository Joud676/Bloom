package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;


public class PlantCardController {

    @FXML
    private Label PlantName;

    @FXML
    private ImageView plantImage;

    @FXML
    private Label plantInfo;

    @FXML
    private Button show_Plant1;
    
    Plant plant1 ;

    public void setPlantData(Plant plant) {
    	PlantName.setText(plant.getName());
    	plantInfo.setText(plant.getCareInfo());
    	byte[] imageBytes = plant.getImage();
    	ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
    	Image image = new Image(bis);
    	plantImage.setImage(image);
    	plant1=plant;
    	
    }
    
    
    @FXML
    void move_to_viewPlant(ActionEvent event) {
        try {
            // Load the PlantDetails.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PlantDetails.fxml"));
            Parent root = loader.load();

            // Get the controller for PlantDetails and pass the plant data to it
            PlantDetailsController detailsController = loader.getController();
            detailsController.setPlantData(plant1); 

            // Set up a new stage and scene for the details view
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Plant Details");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
