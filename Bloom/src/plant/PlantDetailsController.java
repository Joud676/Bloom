package plant;

import customer.CustomerHomePageController;
import util.User;
import util.Plant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import java.awt.TextField;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import util.Navigation;
import util.PlantService;

public class PlantDetailsController {

    @FXML
    private Button Fertilization_Button;

    @FXML
    private Label PlantName;

    @FXML
    private Label TimerLabel;

    @FXML
    private javafx.scene.control.TextField TimerInput;

    @FXML
    private Button back_Button;

    @FXML
    private Text careText;

    @FXML
    private Text infoText;

    @FXML
    private ImageView plantImage;

    @FXML
    private Button startTimerButton;

    private Plant currentPlant; // Current plant data
    private Timeline timeline;

    // Constants for time calculations
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int SECONDS_IN_HOUR = 3600;
    private static final int SECONDS_IN_DAY = 86400;
    @FXML
    private AnchorPane mainPan;

    // the action start from this method called by home page class   
    public void setPlantData(Plant plant) {
        this.currentPlant = plant;
        PlantName.setText(plant.getName());
        infoText.setText(plant.getCharacteristic());
        careText.setText(plant.getCareInfo());
        ByteArrayInputStream bis = new ByteArrayInputStream(plant.getImage());
        Image image = new Image(bis);
        plantImage.setImage(image);

        // Update the timer label based on plant data
        updateTimerLabel();

        // Automatically start the timer
        startTimeline();
    }

    @FXML
    void move_to_Fertilization(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/plant/FertilizationPage.fxml"));
            Parent root = loader.load();
            Fertilization_Controller fertilizationController = loader.getController();
            fertilizationController.setFertilizationData(currentPlant);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClick_button(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/customer/CustomerHomePage.fxml"));
            Parent root = loader.load();
            CustomerHomePageController controller = loader.getController();
            controller.setCustomerId(User.getCustomerId());
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void startTimer(ActionEvent event) throws SQLException {
        try {
            int days = Integer.parseInt(TimerInput.getText());
            if (days <= 0) {
                throw new NumberFormatException();
            }

            // Stop the current timeline if it's running
            if (timeline != null) {
                timeline.stop();
            }

            // Update plant watering interval and last watered timestamp
            currentPlant.setWateringIntervalHours(days * 24);
            currentPlant.setLastWatered(new java.sql.Timestamp(System.currentTimeMillis()));

            // Update the plant in the database
            PlantService.updatePlant(currentPlant);

            // Update UI and start the new timer
            updateTimerLabel();
            startTimeline();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a positive number of days.");
        }
    }

    private void updateTimerLabel() {
        long remainingTime = currentPlant.getRemainingTime();
        if (remainingTime > 0) {
            long days = remainingTime / SECONDS_IN_DAY;
            long hours = (remainingTime % SECONDS_IN_DAY) / SECONDS_IN_HOUR;
            long minutes = (remainingTime % SECONDS_IN_HOUR) / SECONDS_IN_MINUTE;
            long seconds = remainingTime % SECONDS_IN_MINUTE;
            TimerLabel.setText(String.format("%d:%02d:%02d:%02d", days, hours, minutes, seconds));
        } else {
            TimerLabel.setText("Time to water your plant!");
        }
    }

    private void startTimeline() {
        if (timeline != null) {
            timeline.stop();
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            updateTimerLabel();
            long remainingTime = currentPlant.getRemainingTime();
            if (remainingTime <= 0) {
                timeline.stop();
                showAlert("Watering Reminder", "Time to water " + currentPlant.getName() + "!");
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void showAlert(String title, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/styles/dialog.css").toExternalForm());
            alert.showAndWait();
        });
    }

}
