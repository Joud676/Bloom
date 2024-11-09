package bloom.plantshop;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.lang.classfile.components.ClassPrinter.Node;

import javafx.event.ActionEvent;

public class PlantDetailsController {

    @FXML
    private AnchorPane mainPan;

    @FXML
    private Pane main_par;

    @FXML
    private Button back_Button;

    @FXML
    private Button setting_button;

    @FXML
    private Button cart_button;

    @FXML
    private Button Home_button;

    @FXML
    private Text waterNeed_TextField;

    @FXML
    private Text tempNeed_TextField;

    @FXML
    private Text sunNeed_TextField;

    @FXML
    private Text plantInfor_TextField;

    @FXML
    private Text plantInfor_TextField1;

    @FXML
    private Text plantInfor_TextField11;


    @FXML
    void onClick_button(ActionEvent event) {
    	try {
            // Load the previous view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerHomePage.fxml"));
            Parent root = loader.load();
            
            // Get current stage from the event
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            
            // Set the scene to the previous page
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
      
    }

}
