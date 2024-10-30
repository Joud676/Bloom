package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.event.ActionEvent;


public class Plant_ViewController {
	 @FXML
	    private Button Home_button;

	    @FXML
	    private Button back_Button;

	    @FXML
	    private Button cart_button;

	    @FXML
	    private AnchorPane mainPan;

	    @FXML
	    private Pane main_par;

	    @FXML
	    private Text plantInfor_TextField;

	    @FXML
	    private Text plantInfor_TextField1;

	    @FXML
	    private Text plantInfor_TextField11;

	    @FXML
	    private Button setting_button;

	    @FXML
	    private Text sunNeed_TextField;

	    @FXML
	    private Text tempNeed_TextField;

	    @FXML
	    private Text waterNeed_TextField;

	    @FXML
	    void onClick_button(ActionEvent event) {
	    	
	    	try {
	            // Load the previous view
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer_View.fxml"));
	            Parent root = loader.load();
	            
	            // Get current stage from the event
	            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	            
	            // Set the scene to the previous page
	            stage.setScene(new Scene(root));
	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    	

	    }

}
