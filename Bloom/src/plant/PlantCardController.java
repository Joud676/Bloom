package plant;

import customer.CustomerHomePageController;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javafx.scene.input.MouseEvent;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import util.Plant;
import util.User;
import util.DatabaseConnection;

public class PlantCardController {

    @FXML
    private Label PlantName;

    @FXML
    private ImageView plantImage;

    @FXML
    private Label plantInfo;

    @FXML
    private Button show_Plant1;

    Plant plant1;
    Connection connection;

    public void setPlantData(Plant plant) {
        this.plant1 = plant;
        PlantName.setText(plant.getName());
        plantInfo.setText(plant.getCareInfo());
        byte[] imageBytes = plant.getImage();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        Image image = new Image(bis);
        plantImage.setImage(image);

    }

    @FXML
    void move_to_viewPlant(ActionEvent event) {
        try {
            // Load the PlantDetails.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/plant/PlantDetails.fxml"));
            Parent root = loader.load();

            // Get the controller for PlantDetails and pass the plant data to it
            PlantDetailsController detailsController = loader.getController();
            detailsController.setPlantData(plant1);

            // Set up a new stage and scene for the details view
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Plant Details");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteFromCollection(MouseEvent event) {
        int customerId = User.getCustomerId();
        int plantId = plant1.getPlantId();

        if (showConfirmationDialog()) {
            connection = DatabaseConnection.connectDB();
            try {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM plantcollection WHERE customerId = ? AND plantId = ?");
                stmt.setInt(1, customerId);
                stmt.setInt(2, plantId);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Deletion Successful",
                            "Plant Removed from Collection",
                            "The plant has been successfully removed from your collection.");
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/customer/CustomerHomePage.fxml"));
                        Parent root = loader.load();
                        CustomerHomePageController controller = loader.getController();
                        controller.setCustomerId(customerId);
                        Stage stage = (Stage) ((javafx.scene.Node) show_Plant1).getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "SQL Error", "Database Error", "An error occurred while accessing the database.");

            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Deletion Canceled",
                    "Action Canceled",
                    "The plant deletion has been canceled.");
        }
    }

    private boolean showConfirmationDialog() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Are you sure you want to delete this plant from your collection?");
        confirmationAlert.setContentText("This action cannot be undone.");

        ButtonType confirmButton = new ButtonType("Confirm");
        ButtonType cancelButton = new ButtonType("Cancel");
        confirmationAlert.getDialogPane().getStylesheets().add(getClass().getResource("/styles/dialog.css").toExternalForm());

        confirmationAlert.getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result.isPresent() && result.get() == confirmButton;
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/styles/dialog.css").toExternalForm());
        alert.showAndWait();
    }

}
