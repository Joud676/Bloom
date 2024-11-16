import java.awt.Label;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;

public class BrowseCardController implements Initializable {

	@FXML
	private Button plantAdd;

	@FXML
	private AnchorPane plantCard;

	@FXML
	private ImageView plantImage;

	@FXML
	private Label plantName;

	@FXML
	private Label plantPrice;

	@FXML
	private Spinner<?> plantQuan;

	@FXML
	private Label plantInfo;

	private Image image;
	private Plant plant;
	
	private SpinnerValueFactory<Integer> spinner;

	public void setPlantData(Plant plant) {
		this.plant = plant;
		plantName.setText(plant.getName());
		plantPrice.setText(String.valueOf(plant.getPrice()));
		byte[] imageBytes = plant.getImage();
		ByteArrayInputStream bytes = new ByteArrayInputStream(imageBytes);
		image = new Image(bytes, 80, 90, true, true);
		plantImage.setImage(image);

	}

	private int qty;

    public void setQuantity() {
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        plantQuan.setValueFactory(spinner);
    }
    
	@FXML
	void addToCart(ActionEvent event) {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
