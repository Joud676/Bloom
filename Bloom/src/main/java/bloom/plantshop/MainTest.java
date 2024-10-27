package bloom.plantshop;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file for the splash screen
        Parent root = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
        
        // Set up the scene and stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Bloom");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Create a Timeline for the delay
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            // Switch to the starting interface after 5 seconds
            try {
                Parent startingRoot = FXMLLoader.load(getClass().getResource("starting1.fxml"));
                primaryStage.setScene(new Scene(startingRoot));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        
        // Start the timeline
        timeline.setCycleCount(1); // Only run once
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
