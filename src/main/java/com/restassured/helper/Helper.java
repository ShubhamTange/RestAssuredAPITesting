package com.restassured.helper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.restassured.constants.EndPoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Helper {

	public static String userID = "";
	public static String token = "";
	public static String ISBN1 = "";
	public static String ISBN2 = "";
	public static String ISBN3 = "";
	public Helper() {
		RestAssured.baseURI = "https://bookstore.toolsqa.com/";

	}

	public Response userRegistration(String username, String password) {
		System.out.println("========================= A. User Registration  ==========================");
		
		JSONObject request = new JSONObject();
		request.put("userName", username);
		request.put("password", password);

		Response resp = RestAssured.given().contentType(ContentType.JSON).body(request)
				.post(EndPoints.USER_REGISTRATION).andReturn();

		String responseBody = resp.getBody().asString();

		// Extract the value of 'userid' from the JSON response
		userID = resp.jsonPath().getString("userID");

		System.out.println("Registration Success\nUSERID = " + userID);
		System.out.println("Status Code = " + resp.statusCode());
		System.out.println("Response : " + responseBody);

		return resp;
	}

	public Response generateToken(String username, String password) {

		System.out.println("========================= B. Generate Token  ==========================");
		JSONObject request = new JSONObject();
		request.put("userName", username);
		request.put("password", password);

		Response resp = RestAssured.given().contentType(ContentType.JSON).body(request).post("Account/V1/GenerateToken").andReturn();

		token = resp.jsonPath().getString("token");
		System.out.println("token: ll "+ token);
		System.out.println("Response Body : " + resp.getBody().asString());

		return resp;

	}

	public Response getAllBooks() {

		System.out.println("========================= C. Get ALL Book  ==========================");
		Response resp = RestAssured.given().contentType(ContentType.JSON).get(EndPoints.GET_ALLBOOKS);

		System.out.println("Response Body : " + resp.getBody().asPrettyString());

		ISBN1 = resp.jsonPath().getString("books[0].isbn");
		ISBN2 = resp.jsonPath().getString("books[1].isbn");
		ISBN3 = resp.jsonPath().getString("books[2].isbn");
		System.out.println("ISBN1: " + ISBN1);
		System.out.println("ISBN2: " + ISBN2);
		System.out.println("ISBN3: " + ISBN3);
		return resp;
	}

	public Response getBookByISBN() {
		System.out.println("========================= D. Get Book By ISBN  ==========================");
		Response resp = RestAssured
				.given().contentType(ContentType.JSON)
				.queryParam("ISBN", ISBN1)
				.when()
				.get(EndPoints.GET_BOOKSBYISBN).andReturn();

		System.out.println("Response Body : " + resp.getBody().asPrettyString());
		System.out.println("Status Code : " + resp.getStatusCode());
		return resp;

	}


	public Response addListOfBooks() {
		System.out.println("========================= E. add List Of Books ==========================");
		JSONObject request = new JSONObject();
		request.put("userId", userID);
		
		
		JSONArray collectionOfIsbns = new JSONArray();
		JSONObject isbnObject = new JSONObject();
        isbnObject.put("isbn", ISBN1);
        collectionOfIsbns.add(isbnObject);
        
        request.put("collectionOfIsbns", collectionOfIsbns);
		
        System.out.println("Request : " + request);
		Response resp = RestAssured
				.given().contentType(ContentType.JSON)
				.header("Authorization","Bearer " +token)
				.body(request)
				.post(EndPoints.ADD_LISTOFBOOKS);

		System.out.println("Response Body : " + resp.getBody().asPrettyString());
		System.out.println("Status Code : " + resp.getStatusCode());
		return resp;
		
	}
	
public Response updateBookList() {
		
		System.out.println("========================= F. update Book List  ==========================");
		JSONObject request = new JSONObject();
		request.put("userId", userID);
		request.put("isbn", ISBN3);
		System.out.println(request);
		
		Response resp = RestAssured
				.given().contentType(ContentType.JSON).header("Authorization","Bearer " +token)
				.pathParam("ISBN", ISBN1)
				.body(request)
				.put(EndPoints.UPDATE_BOOKLIST);

		System.out.println("Response Body : " + resp.getBody().asPrettyString());
		System.out.println("Status Code : " + resp.getStatusCode());
		return resp;

	}
	
	public Response deleteBooks() {
		System.out.println("========================= H. Delete Book ==========================");
		JSONObject request = new JSONObject();
		request.put("isbn", ISBN3);
		request.put("userId", userID);
		
		System.out.println(request);
		Response resp = RestAssured
				.given().header("Authorization","Bearer " +token)
				.contentType(ContentType.JSON)
				.body(request.toString()).when()
				.delete(EndPoints.DELETE_BOOK);

		System.out.println("Response Body : " + resp.getBody().asPrettyString());
		System.out.println("Status Code : " + resp.getStatusCode());
		return resp;

	}

	
	public static void main(String[] args) {
		Helper help = new Helper();
		help.userRegistration("SHuham_m66", "Shubham@123");
		help.generateToken("SHuham_m66", "Shubham@123");
		help.getAllBooks();
		help.getBookByISBN();
		help.addListOfBooks();
		help.updateBookList();
		help.deleteBooks();
		System.out.println("Finished Successfully...");
	}
}
