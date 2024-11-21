package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Navigation {

    private static String lastPage = "";

    public static void navigateTo(String fxmlFile, Stage currentStage) {
        try {

            Parent root = FXMLLoader.load(Navigation.class.getResource(fxmlFile));

            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setLastPage(String page) {
        lastPage = page;
    }

    public static String getLastPage() {
        return lastPage;
    }
}
