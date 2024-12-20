package util;

import util.DatabaseConnection;
import util.Plant;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlantService {

    public static List<Plant> getPlantsForCustomer(int customerId) {
        List<Plant> plants = new ArrayList<>();
        String query = "SELECT p.plantId, p.plantName, p.characteristics, p.careInfo, p.price, p.quantity, p.image, "
                + "p.fertilization_list "
                + "FROM plant p "
                + "INNER JOIN plantcollection pc ON p.plantId = pc.plantId "
                + "WHERE pc.customerId = ? "
                + "GROUP BY p.plantId, p.plantName, p.characteristics, p.careInfo, p.price, p.quantity, p.image, p.fertilization_list";

        try (Connection connection = DatabaseConnection.connectDB(); PreparedStatement statement = connection.prepareStatement(query)) {
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
                    plants.add(plant);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plants;

    }

    public static void updatePlant(Plant plant) throws SQLException {
        String query = "UPDATE plant SET lastWatered = ?, wateringIntervalHours = ? WHERE plantId = ?";
        try (Connection connection = DatabaseConnection.connectDB() ;
            PreparedStatement statement = connection.prepareStatement(query)){
            statement.setTimestamp(1, plant.getLastWatered());
                statement.setInt(2, plant.getWateringIntervalHours());
                statement.setInt(3, plant.getPlantId());
                statement.executeUpdate();
            }catch (SQLException e) {
            e.printStackTrace();
        }
        }

    }
