package application;

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

	public void uploadPlant(Plant plant) {
		if (plant != null) {
			plants.add(plant);
			System.out.println("Plant " + plant.getName() + " has been successfully uploaded to " + storeName + ".");

			File plantsDirectory = new File("plants/");
			plantsDirectory.mkdirs();

			String plantFileName = "plants/" + plant.getPlantId() + ".txt";
			try (FileWriter writer = new FileWriter(plantFileName)) {
				writer.write(plant.toString());
				System.out.println("Plant " + plant.getName() + " has been saved to " + plantFileName + ".");
			} catch (IOException e) {
				System.out.println("Error saving plant to file: " + e.getMessage());
			}

			String sellerFileName = "users/" + getUsername() + ".txt";
			try (FileWriter writer = new FileWriter(sellerFileName, true)) {
				writer.write(plant.toString() + System.lineSeparator());
				System.out.println("Plant " + plant.getName() + "Details have been saved to your file.");
			} catch (IOException e) {
				System.out.println("Error saving plant details to seller's file: " + e.getMessage());
			}
		} else {
			System.out.println("Error: Plant information is missing.");
		}
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