package bloompro;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UploadTest {

	//Test if the plant object is created with the correct properties


	    // Test 2 check the plant object creation
	    @Test
	    public void testPlantCreation() {
	        List<String> fertilization = Arrays.asList("Fertilizer A", "Fertilizer B");
	        Plant plant = new Plant(1, "Rose", "Beautiful and fragrant", "Water daily", 15.0, 10, fertilization);

	        assertEquals(1, plant.getPlantId());
	        assertEquals("Rose", plant.getName());
	        assertEquals("Beautiful and fragrant", plant.getCharacteristic());
	        assertEquals("Water daily", plant.getCareInfo());
	        assertEquals(15.0, plant.getPrice());
	        assertEquals(10, plant.getQuantity());
	        assertEquals(fertilization, plant.getFertilization());
	    }

	    // Test 2 check the setter methods
	    @Test
	    public void testSetters() {
	        Plant plant = new Plant(0, "", "", "", 0.0, 0, null);

	        plant.setPlantId(2);
	        plant.setName("Tulip");
	        plant.setCharacteristic("Colorful");
	        plant.setCareInfo("Water weekly");
	        plant.setPrice(20.0);
	        plant.setQuantity(5);
	        List<String> fertilization = Arrays.asList("Fertilizer C");
	        plant.setFertilization(fertilization);

	        assertEquals(2, plant.getPlantId());
	        assertEquals("Tulip", plant.getName());
	        assertEquals("Colorful", plant.getCharacteristic());
	        assertEquals("Water weekly", plant.getCareInfo());
	        assertEquals(20.0, plant.getPrice());
	        assertEquals(5, plant.getQuantity());
	        assertEquals(fertilization, plant.getFertilization());
	    }

	    // Test the checkFertilization method 4 compatibility
	    @Test
	    public void testCheckFertilization() {
	        Plant plant1 = new Plant(1, "Rose", "Beautiful", "Water daily", 15.0, 5, Arrays.asList("Tulip"));
	        Plant plant2 = new Plant(2, "Tulip", "Colorful", "Water weekly", 12.0, 3, Arrays.asList("Rose"));

	        String result = Plant.checkFertilization(plant1, plant2);
	        assertEquals("The plants Rose and Tulip can be fertilized together.", result);
	    }

	    // Test the checkFertilization method 4 incompatibility
	    @Test
	    public void testCheckFertilizationIncompatibility() {
	        Plant plant1 = new Plant(1, "Rose", "Beautiful", "Water daily", 15.0, 5, Arrays.asList("Lily"));
	        Plant plant2 = new Plant(2, "Tulip", "Colorful", "Water weekly", 12.0, 3, Arrays.asList("Orchid"));

	        String result = Plant.checkFertilization(plant1, plant2);
	        assertEquals("The plants Rose and Tulip cannot be fertilized together.", result);
	    }

	    // Test the toString method
	    @Test
	    public void testToString() {
	        List<String> fertilization = Arrays.asList("Fertilizer A", "Fertilizer B");
	        Plant plant = new Plant(1, "Rose", "Beautiful and fragrant", "Water daily", 15.0, 10, fertilization);

	        String expectedString = "\nPlant ID: 1\n" +
	                "Name: Rose\n" +
	                "Characteristics: Beautiful and fragrant\n" +
	                "Care Information: Water daily\n" +
	                "Price: 15.0\n" +
	                "Quantity: 10\n" +
	                "Fertilization: Fertilizer A, Fertilizer B";

	        assertEquals(expectedString, plant.toString());
	    }
	}