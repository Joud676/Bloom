package application;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class SplashScreenController {
    
    @FXML
    private ImageView HomeIndicator;

    @FXML
    public void initialize() {
      
    }
    
    @FXML
    public void exit() {
        System.exit(0);

    }
}
