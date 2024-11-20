package operations;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class WelcomePageController {
    @FXML
    private Button startButton;

    @FXML
    public void initialize() {
        // Initialization code here, if needed
    }

    @FXML
    public void getStarted() {
        try {
            // Load the next FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/operations/StartingPage.fxml"));
            Parent root = loader.load();
            
            // Get the current stage and set the new scene
            Stage stage = (Stage) startButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error dialog)
        }
    }
    @FXML
    public void exit() {
        System.exit(0);

    }
}
