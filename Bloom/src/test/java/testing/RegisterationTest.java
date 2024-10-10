package testing;

import static org.junit.jupiter.api.Assertions.*;
import bloom.plantshop.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.Duration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegisterationTest {

	User user;
	File userFile;
	String username, email, password, isSeller, storeName;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@Test
	@Order(1)
	void testSuccessfulSeller() {
		username = "testSeller\n";
		email = "test@example.com\n";
		password = "Password123!\n";
		isSeller = "y\n";
		storeName = "Test Store\n";

		String input = username + email + password + isSeller + storeName;
		// simulate user input using System.setIn if necessary
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		user = User.register();

		assertNotNull(user);
		assertEquals("testSeller", user.getUsername());
		assertEquals("test@example.com", user.getEmail());
		assertEquals("Password123!", user.getPassword());
		assertEquals("Seller", user.getAccountType());

	}

	@Test
	@Order(2)
	void testSuccessfulCustomer() {
		username = "testCustomer\n";
		email = "test@example.com\n";
		password = "Password123!\n";
		isSeller = "n\n";

		String input = username + email + password + isSeller;
		// simulate user input using System.setIn if necessary
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		user = User.register();

		assertNotNull(user);
		assertEquals("testCustomer", user.getUsername());
		assertEquals("test@example.com", user.getEmail());
		assertEquals("Password123!", user.getPassword());
		assertEquals("Customer", user.getAccountType());
	}

	@Test
	@Order(3)
	void testEmptyFields() {
		username = "\n";
		email = "\n";
		password = "\n";
		isSeller = "\n";

		String input = username + email + password + isSeller;

		// capture console output
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(outputStream));

		// simulate user input using System.setIn if necessary
		try (InputStream in = new ByteArrayInputStream(input.getBytes())) {
			System.setIn(in);
			User.register(); // Call the registration method
		} catch (Exception e) {

		} finally {
			// restore original System.out
			System.setOut(originalOut);
		}

		String output = outputStream.toString().trim();

		// check for error messages
		assertTrue(output.contains("All fields are required."), "Expected error message not found in the output.");

		assertNull(user);

	}

	@Test
	@Order(4)
	void testExistingUsername() {
		username = "testCustomer\n";
		email = "test@example.com\n";
		password = "Password123!\n";
		isSeller = "n\n";

		String input = username + email + password + isSeller;

		// capture console output
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(outputStream));

		// simulate user input using System.setIn if necessary
		try (InputStream in = new ByteArrayInputStream(input.getBytes())) {
			System.setIn(in);
			User.register(); // Call the registration method
		} catch (Exception e) {

		} finally {
			// Restore original System.out
			System.setOut(originalOut);
		}

		String output = outputStream.toString().trim();

		// check for error messages
		assertTrue(output.contains("Username already exists. Please try again."),
				"Expected error message not found in the output.");
		assertNull(user);

	}

	@Test
	@Order(5)
	void testInvalidEmail() {
		username = "testCase5\n";
		email = "test@example\n";
		password = "Password123!\n";
		isSeller = "n\n";

		String input = username + email + password + isSeller;

		// capture console output
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(outputStream));

		// simulate user input using System.setIn if necessary
		try (InputStream in = new ByteArrayInputStream(input.getBytes())) {
			System.setIn(in);
			User.register(); // Call the registration method
		} catch (Exception e) {

		} finally {
			// Restore original System.out
			System.setOut(originalOut);
		}

		String output = outputStream.toString().trim();
		

		// check for error messages
		assertTrue(output.contains("Invalid email format. Please try again."),
				"Expected error message not found in the output.");
		assertNull(user);

	}

	@Test
	@Order(6)
	void testInvalidPassword() {
		username = "testCase6\n";
		email = "test@example.com\n";
		password = "Password123\n";
		isSeller = "n\n";

		String input = username + email + password + isSeller;

		// capture console output
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(outputStream));

		// simulate user input using System.setIn if necessary
		try (InputStream in = new ByteArrayInputStream(input.getBytes())) {
			System.setIn(in);
			User.register(); // Call the registration method
		} catch (Exception e) {

		} finally {
			// Restore original System.out
			System.setOut(originalOut);
		}

		String output = outputStream.toString().trim();

		// check for error messages
		assertTrue(output.contains(
				"Password must be at least 8 characters long and contain a mix of upper and lower case letters, numbers, and special characters."),
				"Expected error message not found in the output.");
		assertNull(user);

	}

	@Test
	@Order(7)
	void testInvalidUserType() {
		username = "testCase7\n";
		email = "test@example.com\n";
		password = "Password123!\n";
		isSeller = "m\n";

		String input = username + email + password + isSeller;

		// capture console output
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(outputStream));

		// simulate user input using System.setIn if necessary
		try (InputStream in = new ByteArrayInputStream(input.getBytes())) {
			System.setIn(in);
			User.register(); // Call the registration method
		} catch (Exception e) {

		} finally {
			// Restore original System.out
			System.setOut(originalOut);
		}

		String output = outputStream.toString().trim();

		// check for error messages
		assertTrue(output.contains("Invalid input. Please enter 'y' if you are registering as a seller or 'n' for no."),
				"Expected error message not found in the output.");
		assertNull(user);

	}

	@Test
	@Order(8)
	void testSuccessfulSellerFile() {
		try {
			Thread.sleep(Duration.ofMillis(5000));
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		username = "testSeller";

		// test if the user was stored
		userFile = new File("users/" + username + ".txt");
		boolean actual = checkFile(userFile);
		assertTrue(actual == true);
	}

	@Test
	@Order(9)
	void testSuccessfulCustomerFile() {
		try {
			Thread.sleep(Duration.ofMillis(5000));
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		username = "testCustomer";

		// test if the user was stored
		userFile = new File("users/" + username + ".txt");
		boolean actual = checkFile(userFile);
		assertTrue(actual == true);
	}
	@Test
	@Order(10)
	void testInvalidCustomerFile() {
		try {
			Thread.sleep(Duration.ofMillis(5000));
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		username = "testCase5";
		
		// test if the user was stored
		userFile = new File("users/" + username + ".txt");
		boolean actual = checkFile(userFile);
		// an invalid registration should not be logged 
		assertTrue(actual == false);
	}

	public static boolean checkFile(File userFile) {
		boolean found;

		// check if the file exists
		if (userFile.exists()) {
			found = true;
			System.out.println("User's file is found.");
		} else {
			found = false;
			System.out.println("File not found.");
		}

		return found;
	}

}
