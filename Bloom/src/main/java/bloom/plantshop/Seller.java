package bloom.plantshop;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Seller extends User {
	private String storeName;
	private List<Plant> plants = new ArrayList<>();
	

	public Seller(String username, String email, String password, String type, String storeName) {
		super(username, email, password, type);
		this.setStoreName(storeName);
	}

	public List<Plant> getPlants() {
		return plants;
	}


	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	@Override
	public String toString() {

		String sellerData = (super.toString() + "\nStore name: " + storeName);

		return sellerData;

}
}
