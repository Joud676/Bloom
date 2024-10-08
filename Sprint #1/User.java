package bloom;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

class User {

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

		return super.toString();
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

			System.out.print("Enter username: ");
			String username = scanner.nextLine().trim();

			System.out.print("Enter email: ");
			String email = scanner.nextLine().trim();

			System.out.print("Enter password: ");
			String password = scanner.nextLine().trim();

			System.out.print("Are you registering to sell plants? y/n ");
			String type = scanner.nextLine().toLowerCase().trim();

			// validate inputs
			if (username.isEmpty() || email.isEmpty() || password.isEmpty() || type.isEmpty()) {
				System.out.println("All fields are required. Please try again.");
				continue;
			}

			if (!isValidEmail(email)) {
				while (!isValidEmail(email)) {
					System.out.println("Invalid email format. Please try again.");
					System.out.print("Please enter a valid Email: ");
					email = scanner.nextLine().trim();
				}
			}

			if (!isValidPassword(password)) {
				while (!isValidPassword(password)) {
					System.out.println(
							"Password must be at least 8 characters long and contain a mix of upper and lower case letters, numbers, and special characters.");
					System.out.print("Please enter a valid password: ");
					password = scanner.nextLine().trim();

				}
			}

			// create accounts
			String accountType = type.substring(0, 1);
			User user = null;

			if (!accountType.equals("y") && !accountType.equals("n")) {
				while (!accountType.equals("y") && !accountType.equals("n")) {
					System.out.print("Are you registering to sell plant? Please answer with y for yes or n for no. ");
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

			File F = new File("users/");
			F.mkdirs();
			String fileName = username + ".txt";
			File f = new File("users/"+fileName);
			
			try {
				if (f.createNewFile()) {
					System.out.println(
							"Welcome " + username + "! Your account has been registered successfully.\n" + userData);
				} else {
					System.out.println("Username already exists. Please try again.");
					continue;
				}

			} catch (FileNotFoundException ex) {

				ex.printStackTrace();

			} catch (IOException ex) {

				ex.printStackTrace();
			}
			try {
				FileWriter writeUser = new FileWriter(f);
				writeUser.write(userData);
				writeUser.close();
			}

			catch (IOException e) {
				e.printStackTrace();
			}

			scanner.close();
			return user;

		}

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

	public static void main(String[] args) {
		register();
	}
}

class Seller extends User {
	private String storeName;

	public Seller(String username, String email, String password, String type, String storeName) {
		super(username, email, password, type);
		this.setStoreName(storeName);

	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	@Override
	public String toString() {

		String sellerData = ("Username: " + getUsername() + "\nEmail: " + getEmail() + "\nPassword: " + getPassword()
				+ "\nAccount type: " + getAccountType() + "\nStore name: " + getStoreName());

		return sellerData;
	}

}

class Customer extends User {

	public Customer(String username, String email, String password, String type) {
		super(username, email, password, type);

	}

	@Override
	public String toString() {

		String customerData = ("Username: " + getUsername() + "\nEmail: " + getEmail() + "\nPassword: " + getPassword()
				+ "\nAccount type: " + getAccountType());

		return customerData;
	}

}
