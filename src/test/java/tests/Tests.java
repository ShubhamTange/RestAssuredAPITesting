package tests;

import static org.testng.Assert.assertEquals;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.restassured.helper.Helper;

import io.restassured.response.Response;

public class Tests {
	private Helper help;

//	help.userRegistration("SHuham_m51", "Shubham@123");
//	help.generateToken("SHuham_m51", "Shubham@123");
//	help.getAllBooks();
//	help.getBookByISBN();
//	help.addListOfBooks();
//	help.updateBookList();
//	help.deleteBooks();
//	System.out.println("Finished Successfully...");
	@BeforeClass
	public void init() {
		help = new Helper();
		
	}

	@Test(priority = 1)
	public void testUserRegistration() {

		Response response = help.userRegistration("SHuham_m651", "Shubham@15");
		System.out.println("Status Code: "+response.getStatusCode());
		assertEquals(HttpStatus.SC_CREATED, response.getStatusCode());
	}

	@Test(priority = 2)
	public void testGenerateToken() {
		Response response = help.generateToken("SHuham_m65", "Shubham@15");
		System.out.println("Status Code: "+response.getStatusCode());
		System.out.println("USER ID: "+help.userID);
		assertEquals(HttpStatus.SC_OK, response.getStatusCode());
	}

	@Test(priority = 3)
	public void testGetAllBooks() {
		Response response = help.getAllBooks();
		System.out.println("Status Code: "+response.getStatusCode());
		System.out.println("TOKEN : "+help.token);
		assertEquals(HttpStatus.SC_OK, response.getStatusCode());
	}

	@Test(priority = 4)
	public void testGetBookByISBN() {
		Response response = help.getBookByISBN();
		System.out.println("Status Code: "+response.getStatusCode());
		assertEquals(HttpStatus.SC_OK, response.getStatusCode());
	}

	@Test(priority = 5)
	public void testAddListOfBooks() {
		Response response = help.addListOfBooks();

		assertEquals(HttpStatus.SC_CREATED, response.getStatusCode());

	}

	@Test(priority = 6)
	public void testUpdateBookList() {

		Response response = help.updateBookList();
		System.out.println("Status Code: "+response.getStatusCode());
		assertEquals(HttpStatus.SC_OK, response.getStatusCode());
	}

	@Test(priority = 7)
	public void deleteBook() {

		Response response = help.deleteBooks();
		System.out.println("Status Code: "+response.getStatusCode());
		assertEquals(HttpStatus.SC_NO_CONTENT, response.getStatusCode());
	}
}
