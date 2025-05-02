package testcases;

import static org.testng.Assert.assertEquals;

import java.util.Random;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.restassured.helper.Helper;

import io.restassured.response.Response;

public class GetAllBooks_Test {
	private Helper help;
	String username ="";
	String password = "Shubham@15";
	@BeforeClass
	public  void init() {
		help = new Helper();
		username = new Random().ints(8, 0, 62)
			    .mapToObj(i -> "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(i))
			    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
			    .toString();
		
		System.out.println("RestAssured Test ...");
		System.out.println("Username: "+ username + ", Password: "+ password);
	}

	@Test(priority = 1)
	public void testUserRegistration() {

		Response response = help.userRegistration(username, password);
		System.out.println("Status Code: "+response.getStatusCode());
		assertEquals(HttpStatus.SC_CREATED, response.getStatusCode());
	}
	
	
	@Test(priority = 2)
	public void testGenerateToken() {
		Response response = help.generateToken(username, password);
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
}
