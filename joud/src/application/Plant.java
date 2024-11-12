package application;
import java.util.List;


public class Plant {

		private int plantId;
		private String name;
		private String characteristic;
		private String careInfo;
		private Double price;
		private int quantity;
		private List<String> fertilization;
		private byte[] image; 

		public Plant(int plantId, String plantName, String characteristics, String careInfo, double price, int quantity, List<String> fertilization, byte[] image) {
	        this.plantId = plantId;
	        this.name = plantName;
	        this.characteristic = characteristics;
	        this.careInfo = careInfo;
	        this.price = price;
	        this.quantity = quantity;
	        this.fertilization = fertilization;
	        this.image = image;
	    }
// second constructor without image
		public Plant(int plantId2, String name2, String characteristic2, String careInfo2, double price2, int quantity2,
				List<String> fertilization2) {
			 this.plantId = plantId2;
		        this.name = name2;
		        this.characteristic = characteristic2;
		        this.careInfo = careInfo2;
		        this.price = price2;
		        this.quantity = quantity2;
		        this.fertilization = fertilization2;
		
		}

		public void setPlantId(int plantId) {
			this.plantId = plantId;
		}

		public void setFertilization(List<String> fertilization) {
			this.fertilization = fertilization;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

		public void setCareInfo(String careInfo) {
			this.careInfo = careInfo;
		}

		public void setCharacteristic(String characteristic) {
			this.characteristic = characteristic;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setImage( byte[] image) {
			this.image = image;
		}
		
		
  // Database Retrieval 


		public byte[] getImage() {
			return image;
		}

		public int getPlantId() {
			return plantId;
		}

		public String getName() {
			return name;
		}

		public String getCharacteristic() {
			return characteristic;
		}

		public String getCareInfo() {
			return careInfo;
		}

		public Double getPrice() {
			return price;
		}

		public List<String> getFertilization() {
			return fertilization;
		}

		public String plantDetails(int plantId) {
			return toString();
		}

		public int checkQuantity(int plantId) {

			return quantity;

		}

		public int getQuantity() {
			return quantity;

		}

		public static String checkFertilization(Plant plant1, Plant plant2) {
			if (plant1.getFertilization().contains(plant2.getName())
					&& plant2.getFertilization().contains(plant1.getName())) {
				return "The plants " + plant1.getName() + " and " + plant2.getName() + " can be fertilized together.";
			} else {
				return "The plants " + plant1.getName() + " and " + plant2.getName() + " cannot be fertilized together.";
			}
		}

		@Override
		public String toString() {
			return "Plant ID: " + plantId + "\n" + "Name: " + name + "\n" + "Characteristics: " + characteristic + "\n"
					+ "Care Information: " + careInfo + "\n" + "Price: " + price + "\n" + "Quantity: " + quantity + "\n"
					+ "Fertilization: " + String.join(", ", fertilization);
		}

	}
