package bloom.plantshop;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class User {

	private String username;
	private String email;
	private String password;
	private String accountType;

	public User(String username, String email, String password, String type) {

		this.setUsername(username);
		this.setEmail(email);
		this.setPassword(password);
		this.setAccountType(type);

	}

	@Override
	public String toString() {
		return ("Username: " + username + "\nEmail: " + email + "\nPassword: " + password + "\nAccount type: "
				+ accountType);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public static User register() {

		Scanner scanner = new Scanner(System.in);

		while (true) {

			String username = getInput(scanner, "Enter username: ");

			String email = getInput(scanner, "Enter E-mail: ");

			String password = getInput(scanner, "Enter password: ");

			System.out.print("Are you registering to sell plants? y/n ");
			String type = scanner.nextLine().toLowerCase().trim();

			// validate inputs
			// first eliminate empty inputs
			while (username.isEmpty() || email.isEmpty() || password.isEmpty() || type.isEmpty()) {
				System.out.println("All fields are required.");

				if (username.isEmpty()) {
					System.out.print("Please enter a username: ");
					username = scanner.nextLine().trim();
				}
				if (email.isEmpty()) {
					System.out.print("Please enter your Email: ");
					email = scanner.nextLine().trim();
				}
				if (password.isEmpty()) {
					System.out.print("Please enter a password: ");
					password = scanner.nextLine().trim();

				}
				if (type.isEmpty()) {
					System.out.print("Are you registering to sell plant? Please answer with y for yes or n for no. ");
					type = scanner.nextLine().toLowerCase().trim();
				}

			}

			// invalid email format
			if (!isValidEmail(email)) {
				while (!isValidEmail(email)) {
					System.out.println("Invalid email format. Please try again.");
					System.out.print("Please enter a valid Email: ");
					email = scanner.nextLine().trim();
				}
			}

			// invalid password format
			if (!isValidPassword(password)) {
				while (!isValidPassword(password)) {
					System.out.println(
							"Password must be at least 8 characters long and contain a mix of upper and lower case letters, numbers, and special characters.");
					System.out.print("Please enter a valid password: ");
					password = scanner.nextLine().trim();

				}
			}

			// validate accountType input
			String accountType = type.substring(0, 1);
			User user = null;

			// if the input was invalid
			if (!accountType.equals("y") && !accountType.equals("n")) {
				while (!accountType.equals("y") && !accountType.equals("n")) {
					System.out
							.print("Invalid input. Please enter 'y' if you are registering as a seller or 'n' for no.");
					accountType = scanner.nextLine().toLowerCase().trim();
				}
			}

			if (accountType.equals("y")) {
				type = "Seller";
				System.out.print("Enter your store name: ");
				String storeName = scanner.nextLine();
				user = new Seller(username, email, password, type, storeName);

			} else if (accountType.equals("n")) {
				type = "Customer";
				user = new Customer(username, email, password, type);
			}

			String userData = user.toString();

			// save user's data to a file
			// create a users directory (folder)
			File userDir = new File("users/");
			userDir.mkdirs();
			String fileName = username + ".txt";
			File userFile = new File("users/" + fileName);

			try {
				if (userFile.createNewFile()) {
					System.out.println(
							"Welcome " + username + "! Your account has been registered successfully.\n" + userData);
					try (FileWriter writeUser = new FileWriter(userFile)) {
						writeUser.write(userData);
						writeUser.close();
					}
				} else {
					System.out.println("Username already exists. Please try again.");
					continue;
				}

			} catch (FileNotFoundException ex) {

				ex.printStackTrace();

			} catch (IOException ex) {

				ex.printStackTrace();
			}

			
			return user;

		}

	}

	// getting user inputs
	private static String getInput(Scanner scanner, String prompt) {
		System.out.print(prompt);
		return scanner.nextLine().trim();
	}

	private static boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		return Pattern.matches(emailRegex, email);
	}

	// Password validation method
	private static boolean isValidPassword(String password) {
		String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
		return Pattern.matches(passwordRegex, password);
	}

}
