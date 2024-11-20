/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lama1
 */
public class CheckoutPageController implements Initializable {

    @FXML
    private ImageView returnPage;
    @FXML
    private Pane main_par;
    @FXML
    private Button payBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private AnchorPane cartPane;
    @FXML
    private AnchorPane checkoutPane;
    @FXML
    private Label orderTotalLbl;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ChoiceBox<String> timeChooser;
    @FXML
    private Button confirmBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TableColumn<Cart, String> orderItemCol;
    @FXML
    private TableColumn<Cart, Integer> orderQuanCol;
    @FXML
    private TableColumn<Cart, Double> orderPriceCol;
    @FXML
    private TableView<Cart> itemsTable;
    @FXML
    private TableColumn<Cart, String> cartItemCol;
    @FXML
    private TableColumn<Cart, Integer> cartQuanCol;
    @FXML
    private TableColumn<Cart, Double> cartPriceCol;
    @FXML
    private Label cartTotalLbl;
    private List<Cart> items;
    @FXML
    private TableView<Cart> paymentTable;

    Alert alert;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Connection con = database.connectDB();
        items = new ArrayList();
        cartItemCol.setCellValueFactory(new PropertyValueFactory<>("plantName"));
        cartQuanCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        cartPriceCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        double total = 0.0;
        try {
            PreparedStatement stm = con.prepareStatement("SELECT p.plantId, p.plantName, p.price, c.quantity, (p.price * c.quantity) AS total, p.sellerId"
                    + " FROM plant p JOIN cart c ON p.plantId = c.plantId"
                    + " JOIN customer q ON q.customerId = c.customerId"
                    + " WHERE q.customerId = ? "
                    + "GROUP BY p.plantId, p.plantName, p.price, c.quantity");
            stm.setInt(1, UserId.getCustomerId());
            ResultSet re = stm.executeQuery();

            while (re.next()) {
                total += re.getDouble(5);
                items.add(new Cart(
                        UserId.getCustomerId(),
                        re.getInt(1),
                        re.getString(2),
                        re.getDouble(5),
                        re.getInt(4),
                        re.getInt(6)
                ));
            }
            System.out.println(items);
            itemsTable.getItems().addAll(items);
            cartTotalLbl.setText("$" + total);

        } catch (SQLException ex) {
            Logger.getLogger(CheckoutPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void returnPage(MouseEvent event) {
        if (cartPane.isVisible()) {
            Stage currentStage = (Stage) returnPage.getScene().getWindow();
            Navigation.navigateTo("BrowseCustomerPlants.fxml", currentStage);
        } else {
            cartPane.setVisible(true);
            checkoutPane.setVisible(false);
            paymentTable.getItems().clear();
            timeChooser.getItems().clear();

        }

    }

    @FXML
    private void checkout(ActionEvent event) {
        cartPane.setVisible(false);
        checkoutPane.setVisible(true);
        orderItemCol.setCellValueFactory(new PropertyValueFactory<>("plantName"));
        orderQuanCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        orderPriceCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        paymentTable.getItems().setAll(items);
        double totalFee = Double.parseDouble(cartTotalLbl.getText().substring(1));
        if (totalFee < 99) {
            totalFee += 5;
            Cart del = new Cart(0, 0, "Delivery Fee", 5.0, 1, 0);
            paymentTable.getItems().add(del);
        }

        orderTotalLbl.setText("$" + totalFee);

        String timeslots[] = {"8:00AM - 02:00PM", "05:00PM - 11:00PM"};
        timeChooser.setItems(FXCollections.observableArrayList(timeslots));

        LocalDate todayPlusOne = LocalDate.now().plusDays(1);
        datePicker.setValue(todayPlusOne);

        // Set the last possible day to current date + 15 days
        LocalDate maxDate = LocalDate.now().plusDays(15);
        datePicker.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                // Disable dates after maxDate
                if (item != null && item.isAfter(maxDate)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // Optional: style for disabled cells
                }
            }
        });

    }

    @FXML
    private void removeItem(ActionEvent event) {
        Cart selectedItem = itemsTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            // Remove from TableView
            itemsTable.getItems().remove(selectedItem);
            double newTotal = Double.parseDouble(cartTotalLbl.getText().substring(1)) - selectedItem.getTotal();
            cartTotalLbl.setText("$" + newTotal);

            Connection con = database.connectDB();
            try {
                PreparedStatement stm = con.prepareStatement("DELETE FROM cart WHERE customerId =? and plantId=?");
                stm.setInt(1, selectedItem.getCustomerId());
                stm.setInt(2, selectedItem.getPlantId());
                stm.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(CheckoutPageController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void confirmationAction(ActionEvent event) {
        if (datePicker.getValue() != null && timeChooser.getValue() != null) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Order Confirmed");
            alert.setContentText("Your order has been placed, thank you!");
            ButtonType confirmButton = new ButtonType("Confirm");
            alert.getButtonTypes().setAll(confirmButton);
            Optional<ButtonType> result = alert.showAndWait();

            Connection con = database.connectDB();
            try {
                PreparedStatement stm = con.prepareStatement("DELETE FROM cart WHERE customerId =?");
                stm.setInt(1, UserId.getCustomerId());
                stm.executeUpdate();
                for (int i = 0; i < items.size(); i++) {
                    PreparedStatement pstmt = con.prepareStatement("INSERT INTO  bloom.order (quantity, purchaseDate, totalPrice, deliveryDate, deliveryTime, sellerId, customerId, plantId) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                    pstmt.setInt(1, items.get(i).getQuantity());
                    pstmt.setDate(2, java.sql.Date.valueOf(LocalDate.now())); // Set current date
                    pstmt.setDouble(3, items.get(i).getTotal());
                    pstmt.setDate(4, java.sql.Date.valueOf(datePicker.getValue()));
                    pstmt.setString(5, timeChooser.getValue());
                    pstmt.setInt(6, items.get(i).getSellerId());
                    pstmt.setInt(7, items.get(i).getCustomerId());
                    pstmt.setInt(8, items.get(i).getPlantId());
                    
                    pstmt.executeUpdate();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CheckoutPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
            // user cart is sorted in items list 
            // should be added to orders table and should be added to users collection 
            Stage currentStage = (Stage) returnPage.getScene().getWindow();
            Navigation.navigateTo("CustomerHomePage.fxml", currentStage);
            
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill delivery information.");
            alert.showAndWait();
        }
    }

}
