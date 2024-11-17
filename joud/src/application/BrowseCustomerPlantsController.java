package application;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.cj.jdbc.DatabaseMetaData;

public class BrowseCustomerPlantsController {

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

	    }

	    @FXML
	    void onClick_button(ActionEvent event) {

	    }

	    @FXML
	    void searchPlant(ActionEvent event) {
	    }

	private ObservableList<Plant> cardListData;

	Connection connection;
	ResultSet resultSet;
	PreparedStatement statement;
	private static final String URL = "jdbc:mysql://localhost:3306";
	private static final String USER = "root";
	private static final String PASSWORD = "100398";

	@FXML
	public void initialize() {
		try {
			displayPlantCard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ObservableList<Plant> loadBrowseData() {

		String query = "SELECT * from plant";

		ObservableList<Plant> browseData;
		connection = database.connectDB();

		try {
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			Plant plant;

			while (resultSet.next()) {
				byte[] imageBytes = resultSet.getBytes("image");
				String plantName = resultSet.getString("plantName");
				String characteristics = resultSet.getString("characteristics");
				double price = resultSet.getDouble("price");
				plant = new Plant(imageBytes, plantName, characteristics, price);

				browseData = FXCollections.observableArrayList().add(plant);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return browseData;
	}

	public void displayPlantCard() {

		cardListData = FXCollections.observableArrayList().addAll(loadBrowseData());

		int row = 0;
		int column = 0;

		browseGrid.getChildren().clear();
		browseGrid.getRowConstraints().clear();
		browseGrid.getColumnConstraints().clear();

		for (int i = 0; i < cardListData.size(); i++) {

			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("BrowsePlantCard.fxml"));
				AnchorPane pane = loader.load();
				BrowseCardController plantCard = loader.getController();
				plantCard.setPlantData(cardListData.get(i));
				if (column == 1) {
					column = 0;
					row += 1;
				}

				browseGrid.add(pane, column++, row);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

//	private void setPlantDetails(byte[] buf, String plantName, double price, ImageView imageView, Label plantNameLabel,
//			Label priceLabel) {
//		plantNameLabel.setText(plantName != null ? plantName : "Unknown Plant");
//
//		priceLabel.setText(String.format("%.2f", price));
//
//		if (buf != null) {
//			InputStream inputStream = new ByteArrayInputStream(buf);
//			Image image = new Image(inputStream);
//			imageView.setImage(image);
//		} else {
//			System.err.println("Image byte array is null for " + plantName);
//
//		}
//	}

//	private void retrieveInfo(int index) {
//		String plantName = plantNames[index];
//		double price = plantPrices[index];
//		String characteristics = plantCharacteristics[index];
//
//		Alert alert = new Alert(Alert.AlertType.INFORMATION);
//		alert.setTitle("Plant Information");
//		alert.setHeaderText(plantName);
//		alert.setContentText("Price: " + String.format("%.2f", price) + "\nCharacteristics: " + characteristics);
//		alert.showAndWait();
//	}

	private void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void searchPlant() {

	}

	@FXML
	public void checkOut() {
	}

	@FXML
	public void AddToCart() {
	}

	@FXML
	void onClick_button(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerHomePage.fxml"));
			Parent root = loader.load();

			// Get the CustomerHomePageController
			CustomerHomePageController controller = loader.getController();

			// Set the customer ID and call loadPlantCards again
			int customerId = UserId.getCustomerId();
			System.out.print(customerId + "in the plant details");
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
