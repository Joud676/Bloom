package application;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlantService {

    public static List<Plant> getPlantsForCustomer(int customerId) {
    	List<Plant> plants = new ArrayList<>(); 
    	String query = "SELECT p.plantId, p.plantName, p.characteristics, p.careInfo, p.price, p.quantity, p.image, " 
    	+ "p.fertilization_list" + "FROM plant p " + "INNER JOIN plantcollection pc ON p.plantId = pc.plantId " 
    			+ "WHERE pc.customerId = ? " + "GROUP BY p.plantId"; 
    	try (Connection connection = database.connectDB();
    			PreparedStatement statement = connection.prepareStatement(query)) {
    		statement.setInt(1, customerId); 
    		try (ResultSet resultSet = statement.executeQuery()) { 
    			while (resultSet.next()) { 
    				int plantId = resultSet.getInt("plantId");
    			String name = resultSet.getString("plantName"); 
    			String characteristic = resultSet.getString("characteristics"); 
    			String careInfo = resultSet.getString("careInfo");
    			double price = resultSet.getDouble("price"); 
    			int quantity = resultSet.getInt("quantity"); 
    			String fertilizationStr = resultSet.getString("fertilization_list");
    			List<String> fertilization = fertilizationStr != null ? Arrays.asList(fertilizationStr.split(",")) : new ArrayList<>(); 
    			byte[] image = resultSet.getBytes("image"); 
    			Plant plant = new Plant(plantId, name, characteristic, careInfo, price, quantity, fertilization, image);
    			plants.add(plant); } } } catch (SQLException e) { e.printStackTrace(); 
    			} 
    	return plants;

    }

}
