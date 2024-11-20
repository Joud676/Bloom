package util;

import java.util.ArrayList;
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

    // Constructor with all fields including image
    public Plant(int plantId, String plantName, String characteristics, String careInfo, double price, int quantity, List<String> fertilization, byte[] image) {
        this.plantId = plantId;
        this.name = plantName;
        this.characteristic = characteristics;
        this.careInfo = careInfo;
        this.price = price;
        this.quantity = quantity;
        this.fertilization = (fertilization != null) ? fertilization : new ArrayList<>();
        this.image = image;
    }

    // Constructor without image
    public Plant(int plantId, String name, String characteristic, String careInfo, double price, int quantity, List<String> fertilizationList) {
        this(plantId, name, characteristic, careInfo, price, quantity, fertilizationList, null);
    }

    // Constructor for browsing
    public Plant(byte[] browseImage, String browseName, String browseCharacteristic, double browsePrice,int id,int qun) {
        this(id, browseName, browseCharacteristic, null, browsePrice, qun, null, browseImage);
    }

    // Getters and Setters
    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getCareInfo() {
        return careInfo;
    }

    public void setCareInfo(String careInfo) {
        this.careInfo = careInfo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        if (price != null && price >= 0) {
            this.price = price;
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        }
    }

    public List<String> getFertilization() {
        return fertilization;
    }

    public void setFertilization(List<String> fertilization) {
        this.fertilization = (fertilization != null) ? fertilization : new ArrayList<>();
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    // Static method to check fertilization compatibility
    public static String checkFertilization(Plant plant1, Plant plant2) {
        if (plant1 == null || plant2 == null) {
            return "Invalid plants provided for comparison.";
        }
        List<String> fertilization1 = plant1.getFertilization();
        List<String> fertilization2 = plant2.getFertilization();
        if (fertilization1 != null && fertilization2 != null &&
            fertilization1.contains(plant2.getName()) && fertilization2.contains(plant1.getName())) {
            return "The plants " + plant1.getName() + " and " + plant2.getName() + " can be fertilized together.";
        } else {
            return "The plants " + plant1.getName() + " and " + plant2.getName() + " cannot be fertilized together.";
        }
    }

    @Override
    public String toString() {
        return "Plant ID: " + plantId + "\n" +
               "Name: " + name + "\n" +
               "Characteristics: " + characteristic + "\n" +
               "Care Information: " + careInfo + "\n" +
               "Price: " + price + "\n" +
               "Quantity: " + quantity + "\n" +
               "Fertilization: " + (fertilization != null ? String.join(", ", fertilization) : "None");
    }
}
