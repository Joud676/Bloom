package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

public class CustomerHomePageController {
	 // الخطا هنا انه لازم الداتا الي رجعت من الداتا بيز تروح تتخزن في كلاس البلانت عشان لمن الكارد كنتلرولر يحط البيانات في الكارد  عشان
	// يروح ياخدها من كلاس البلانت مو من الداتا بيز 
	
	
	//attribute for the customer
		int customerId;

		List<Plant> plants = PlantService.getPlantsForCustomer(customerId);
		
		public void setCustomerId(int customerId) {
			
			    this.customerId = customerId;
			    plants = PlantService.getPlantsForCustomer(customerId); // Load plants for this customer
			    
			    // Clear existing plant cards and load them again
			    vboxContainer.getChildren().clear();
			    loadPlantCards();

		}
	
    private final String DB_URL = "jdbc:mysql://localhost:3306/bloom";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Rr120178593!";

    @FXML
    private Button Home_button;

    @FXML
    private Button cart_button;

    @FXML
    private AnchorPane mainPan;

    @FXML
    private Pane main_par;

    @FXML
    private Button newPlant_Button;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button search_Button;

    @FXML
    private TextField search_text_Field;

    @FXML
    private Pane searchpan;

    @FXML
    private Button setting_button;

    @FXML
    private VBox vboxContainer;
    
    @FXML
    private ImageView profileImg;
    
    @FXML
    private ImageView profile;
    
    public void loadPlantCards() {
        try {
           vboxContainer.getChildren().clear(); // Clear existing cards if necessary

            for (Plant plant : plants) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("PlantCard.fxml"));
                AnchorPane plantCard = loader.load();
              
                System.out.println(plant+"in the load method");

                // Get the controller for this card and set data
                PlantCardController controller = loader.getController();
                controller.setPlantData(plant);

                // Add the card to the VBox
                vboxContainer.getChildren().add(plantCard);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
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
    void moveTo_Market(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BrowseCustomerPlants.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Scene) newPlant_Button.getScene()).getWindow();
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
    }}
    
     @FXML  
     void toProfile() {
    	 Stage stage = (Stage) newPlant_Button.getScene().getWindow();
         Navigation.navigateTo("CustomerProfile.fxml", stage);   
     }

}




