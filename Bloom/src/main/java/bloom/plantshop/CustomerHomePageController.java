package bloom.plantshop;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class CustomerHomePageController {
	  
    private final String DB_URL = "jdbc:mysql://localhost:3306/bloom";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "100398";

    @FXML
    private Button Button_plant;

    @FXML
    private Button show_Plant2;

    @FXML
    private Button show_Plant3;
    @FXML
    private Button Button_plant1;

    @FXML
    private Button Button_plant2;

    @FXML
    private Button Button_plant3;
    
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
    private Button search_Button;

    @FXML
    private TextField search_text_Field;

    @FXML
    private Pane searchpan;

    @FXML
    private Button setting_button;
    @FXML
    private Button buyButton;
    
    @FXML
    void move_to_viewPlant(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PlantDetails.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }

  
    @FXML
    void onClick_button(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerHomePage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Scene) event.getSource()).getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Customer Home Page");
            stage.show();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Loading Failed", "Error loading Customer Home Page: " + e.getMessage());
        }    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    void buyPlant(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BrowseCustomerPlants.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Scene) buyButton.getScene()).getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Buy Plant");
            stage.show();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Loading Failed", "Error loading Buy Plant Page: " + e.getMessage());
        }    }
    
    @FXML
    void search(ActionEvent event) {
    	
    	String plantName = search_text_Field.getText().toLowerCase();
        String query = "SELECT * FROM plants WHERE LOWER(name) = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, plantName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String plantInfo = "Plant found: " + resultSet.getString("name") +
                                   "\nType: " + resultSet.getString("type") +
                                   "\nWatering Frequency: " + resultSet.getString("watering_frequency");
                JOptionPane.showMessageDialog(null, plantInfo, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Plant not found.", "Search Result", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while searching for the plant.", "Error", JOptionPane.ERROR_MESSAGE);
        

    }

}
}



