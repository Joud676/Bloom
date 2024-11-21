package seller;

import util.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SellerHomePageController {


    @FXML
    private ImageView profileImg;

    @FXML
    private Button addPlant_Button;


    @FXML
    private Button viewPlant_Button;

    @FXML
    private ImageView profile;

    @FXML
    private ImageView cart;

    void initialize() {
        Navigation.setLastPage("/seller/SellerHomePage.fxml");
    }

    @FXML
    void addPlant(ActionEvent event) {
        Stage stage = (Stage) addPlant_Button.getScene().getWindow();
        Navigation.navigateTo("/plant/AddPlant.fxml", stage);
    }

    @FXML
    void toProfile() {
        Stage stage = (Stage) addPlant_Button.getScene().getWindow();
        Navigation.navigateTo("/seller/SellerProfile.fxml", stage);
    }

    @FXML
    void purchaseHistory() {
        Stage stage = (Stage) ((Scene) cart.getScene()).getWindow();
        Navigation.navigateTo("/seller/ViewSellerHistory.fxml", stage);
    }

    @FXML
    void viewPlant(ActionEvent event) {
        Stage stage = (Stage) viewPlant_Button.getScene().getWindow();
        Navigation.navigateTo("/seller/ProductsPage.fxml", stage);
    }
}
