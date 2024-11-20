package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/operations/WelcomePage.fxml"));
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
           primaryStage.getIcons().add(new Image (Main.class.getResourceAsStream("/images/Bloom Logo Green.jpg")));
        primaryStage.show();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
        }));
        
        timeline.setCycleCount(1); 
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
