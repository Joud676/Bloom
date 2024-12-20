package operations;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.event.ActionEvent;

public class StartingPageController {
    @FXML
    private Button login;  // Reference to the login button
    @FXML
    private Button signup;  // Reference to the signup button

    // Event Listener on Button[#login].onAction
    @FXML
    void handleLoginButtonAction(ActionEvent event) {
        try {
            // Load the login FXML
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/operations/login.fxml"));
            // Get the current stage
            Stage stage = (Stage) login.getScene().getWindow();
            // Set the new scene
            stage.setScene(new Scene(loginRoot));
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally, handle the error (e.g., show an alert)
        }
    }

    // Event Listener on Button[#signup].onAction
    @FXML
    public void signup(ActionEvent event) {
        try {
            // Load the signup FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/operations/signup.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) signup.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // You can handle the exception, e.g., show an alert dialog
        }
    }
    @FXML
    public void exit() {
        System.exit(0);

    }
}
