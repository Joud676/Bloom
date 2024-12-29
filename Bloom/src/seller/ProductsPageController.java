package seller;

import util.User;
import util.Navigation;
import util.DatabaseConnection;
import util.Plant;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductsPageController {

    @FXML
    private Button back;

    @FXML
    private GridPane browseGrid;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;

    private ObservableList<Plant> cardListData;

    Connection connection;
    ResultSet resultSet;
    PreparedStatement statement;

    public void initialize() {
        try {
        	cardListData = loadBrowseData();
            displayPlantCard(cardListData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Plant> loadBrowseData() {
        String query = "SELECT * FROM plant WHERE sellerId = ?";
        ObservableList<Plant> browseData = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConnection.connectDB();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, User.getSellerId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    byte[] imageBytes = resultSet.getBytes("image");
                    String plantName = resultSet.getString("plantName");
                    String characteristics = resultSet.getString("characteristics");
                    double price = resultSet.getDouble("price");
                    int id = resultSet.getInt("plantId");
                    int quantity = resultSet.getInt("quantity");

                    Plant plant = new Plant(imageBytes, plantName, characteristics, price, id, quantity);
                    browseData.add(plant);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return browseData;
    }

    public void displayPlantCard(ObservableList<Plant> plantsToDisplay) {
        browseGrid.getChildren().clear(); 
        browseGrid.getRowConstraints().clear(); 
        browseGrid.getColumnConstraints().clear(); 

        int row = 0;
        int column = 0;

        for (int i = 0; i < plantsToDisplay.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/seller/ProductCard.fxml"));
                AnchorPane pane = loader.load();
                ProductCardController plantCard = loader.getController();
                plantCard.setPlantData(plantsToDisplay.get(i));

                if (column == 1) {
                    column = 0;
                    row++;
                }

                browseGrid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(5));

            } catch (Exception e) {
                e.printStackTrace();
            }
        
            }
        }
    

    @FXML
    public void searchPlant() {
        String searchText = searchTextField.getText().toLowerCase().trim();

        // Filter based on search text
        ObservableList<Plant> filteredData = FXCollections.observableArrayList(
                cardListData.filtered(plant -> 
                    plant.getName().toLowerCase().startsWith(searchText) 
                )
        );

        // Display the filtered results
        displayPlantCard(filteredData);
    }

    @FXML
    void onClick_button() {
        Stage currentStage = (Stage) ((Scene) back.getScene()).getWindow();
        Navigation.navigateTo("/seller/SellerHomePage.fxml", currentStage);
    }
}

