package bloom.plantshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = User.register(); // Call the register method from the User class

        // Now create a Seller instance based on user input or registration
        if (user instanceof Seller) {
            Seller seller = (Seller) user; // Assuming the user registered as a seller

            while (true) {
                System.out.print("Do you want to add a new plant? (y/n): ");
                String response = scanner.nextLine().trim().toLowerCase();

                if (!response.equals("y")) {
                    System.out.println("No plant added.");
                    break;
                }

                int plantId = getUniquePlantId(seller, scanner);
                if (plantId == -1) continue;

                System.out.print("Enter plant name: ");
                String name = scanner.nextLine().trim();

                System.out.print("Enter plant characteristics: ");
                String characteristic = scanner.nextLine().trim();

                System.out.print("Enter plant care information: ");
                String careInfo = scanner.nextLine().trim();

                double price = getDoubleInput(scanner, "Enter plant price: ");
                if (price < 0) continue;

                int quantity = getIntInput(scanner, "Enter plant quantity: ");
                if (quantity < 0) continue;

                System.out.print("Enter plant fertilization (comma-separated list): ");
                String[] fertilizationArray = scanner.nextLine().split(",");
                List<String> fertilization = new ArrayList<>();
                for (String fert : fertilizationArray) {
                    fertilization.add(fert.trim());
                }

                if (name.isEmpty() || characteristic.isEmpty() || careInfo.isEmpty() || fertilization.isEmpty()) {
                    System.out.println("Plant name, characteristics, care information, and fertilization requirements cannot be empty.");
                    continue;
                }

                // Create the Plant instance and upload it
                Plant plant = new Plant(plantId, name, characteristic, careInfo, price, quantity, fertilization);
                seller.uploadPlant(plant);
            }
        } else {
            System.out.println("Registered user is not a seller.");
        }
    }

    private static int getUniquePlantId(Seller seller, Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter plant ID: ");
                int plantId = Integer.parseInt(scanner.nextLine().trim());

                // Use the getter method to access the plants list
                boolean idExists = seller.getPlants().stream().anyMatch(existingPlant -> existingPlant.getPlantId() == plantId);
                if (idExists) {
                    System.out.println("Error: Plant ID " + plantId + " already exists. Please enter a unique ID.");
                } else {
                    return plantId;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value for the ID.");
            }
        }
    }

    private static double getDoubleInput(Scanner scanner, String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
    }

    private static int getIntInput(Scanner scanner, String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
    }
}
