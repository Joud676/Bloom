package customer;

import plant.PlantCardController;
import util.PlantService;
import util.Navigation;
import util.DatabaseConnection;
import util.Plant;
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
import javafx.scene.input.MouseEvent;
import util.User;

public class CustomerHomePageController {
    // الخطا هنا انه لازم الداتا الي رجعت من الداتا بيز تروح تتخزن في كلاس البلانت عشان لمن الكارد كنتلرولر يحط البيانات في الكارد  عشان
    // يروح ياخدها من كلاس البلانت مو من الداتا بيز 

    private Button Home_button;

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

    private TextField search_text_Field;

    @FXML
    private VBox vboxContainer;

    @FXML
    private ImageView profileImg;

    //attribute for the customer
    int customerId;

    List<Plant> plants;
    @FXML
    private ImageView cart;
    @FXML
    private ImageView profileIcon;

    void initialize() {
        Navigation.setLastPage("/customer/CustomerHomePage.fxml");

    }

    public void setCustomerId(int customerId) {

        this.customerId = customerId;
        plants = PlantService.getPlantsForCustomer(customerId); // Load plants for this customer

        // Clear existing plant cards and load them again
        vboxContainer.getChildren().clear();
        loadPlantCards();

    }

    public void loadPlantCards() {
        try {
            vboxContainer.getChildren().clear(); // Clear existing cards if necessary

            for (Plant plant : plants) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/plant/PlantCard.fxml"));
                AnchorPane plantCard = loader.load();

                System.out.println(plant + "in the load method");

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

    void move_to_viewPlant(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/plant/PlantDetails.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    void onClick_button(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/customer/CustomerHomePage.fxml"));
            Parent root = loader.load();
            CustomerHomePageController controller = loader.getController();
            int customerId = User.getCustomerId();
            System.out.print(customerId + " in the plant details");
            controller.setCustomerId(customerId);
            Stage stage = (Stage) ((javafx.scene.Node) Home_button).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void moveTo_Market(ActionEvent event) {
        try {
            // Load the FXML file for the MarketPage page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/customer/MarketPage.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Loading Failed");
            alert.setHeaderText("Error loading Buy Plant Page");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            e.printStackTrace();  // This will print the stack trace for more details
        }
    }

    @FXML
    void search(ActionEvent event) {

        String plantName = search_text_Field.getText().toLowerCase();
        String query = "SELECT * FROM plants WHERE LOWER(name) = ?";

        try (Connection connection = DatabaseConnection.connectDB(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, plantName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String plantInfo = "Plant found: " + resultSet.getString("name")
                        + "\nType: " + resultSet.getString("type")
                        + "\nWatering Frequency: " + resultSet.getString("watering_frequency");
                JOptionPane.showMessageDialog(null, plantInfo, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Plant not found.", "Search Result", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while searching for the plant.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    void toProfile() {
        Stage stage = (Stage) newPlant_Button.getScene().getWindow();
        Navigation.navigateTo("/customer/CustomerProfile.fxml", stage);
    }

    @FXML
    void toCart() {
        Stage currentStage = (Stage) newPlant_Button.getScene().getWindow();
        Navigation.navigateTo("/customer/CheckoutPage.fxml", currentStage);
    }

}
