package customer;

import util.User;
import util.Navigation;
import util.DatabaseConnection;
import util.Plant;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MarketPageController {

    @FXML
    private Button back;

    @FXML
    private GridPane browseGrid;

    @FXML
    private Button checkoutBtn;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;

    @FXML
    void checkoutAction(ActionEvent event) {
        Stage currentStage = (Stage) back.getScene().getWindow();
        Navigation.navigateTo("/customer/CheckoutPage.fxml", currentStage);

    }

    private ObservableList<Plant> cardListData;

    Connection connection;
    ResultSet resultSet;
    PreparedStatement statement;

    public void initialize() {
        try {
            displayPlantCard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Plant> loadBrowseData() {
        String query = "SELECT * from plant";

        ObservableList<Plant> browseData = FXCollections.observableArrayList(); // Initialize the ObservableList
        connection = DatabaseConnection.connectDB();

        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            Plant plant;

            while (resultSet.next()) {
                byte[] imageBytes = resultSet.getBytes("image");
                String plantName = resultSet.getString("plantName");
                String characteristics = resultSet.getString("characteristics");
                double price = resultSet.getDouble("price");
                int id = resultSet.getInt("plantId");
                int qun = resultSet.getInt("quantity");
                plant = new Plant(imageBytes, plantName, characteristics, price, id, qun);
                System.out.println(plant);
                browseData.add(plant); // Add plant to the list
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return browseData;
    }

    public void displayPlantCard() {
        cardListData = FXCollections.observableArrayList(loadBrowseData()); // Directly assign the list returned by loadBrowseData()

        int row = 0;
        int column = 0;

        browseGrid.getChildren().clear();
        browseGrid.getRowConstraints().clear();
        browseGrid.getColumnConstraints().clear();

        for (int i = 0; i < cardListData.size(); i++) {
            if (cardListData.get(i).getQuantity() > 0) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/customer/MarketCard.fxml"));
                    AnchorPane pane = loader.load();
                    MarketCardController plantCard = loader.getController();
                    plantCard.setPlantData(cardListData.get(i));
                    if (column == 1) {
                        column = 0;
                        row += 1;
                    }

                    browseGrid.add(pane, column++, row);
                    GridPane.setMargin(pane, new Insets(5));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @FXML
    public void searchPlant() {

    }

    public void checkOut() {

    }

    public void AddToCart() {
    }

    @FXML
    void onClick_button(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/customer/CustomerHomePage.fxml"));
            Parent root = loader.load();

            // Get the CustomerHomePageController
            CustomerHomePageController controller = loader.getController();

            // Set the customer ID and call loadPlantCards again
            int customerId = User.getCustomerId();
            controller.setCustomerId(customerId);

            // Get the current stage and set the scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
