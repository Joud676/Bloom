package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Navigation {

    public static void navigateTo(String fxmlFile, Stage currentStage) {
        try {
            
            Parent root = FXMLLoader.load(Navigation.class.getResource(fxmlFile));

            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
