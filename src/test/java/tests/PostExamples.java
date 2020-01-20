package tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import actions.ActionsClass;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Random;


public class PostExamples extends ActionsClass{
	static Random rand = new Random();

	static int  n = rand.nextInt(50) + 1;
	@Test(description =" POST Example creating a new user")
	public void test_1() {
		loadRegistrationUrl();
		RequestSpecification httpRequest = RestAssured.given();
		JSONObject requestParams = new JSONObject();
		requestParams.put("FirstName", "RestAPI"+n);
		requestParams.put("LastName", "RestAPI"+n);
		requestParams.put("UserName", "RestAPI"+n);
		requestParams.put("Email", "RestAPI"+n+"@test.com");
		requestParams.put("Password", "1234567890");
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(requestParams.toJSONString());
		Response response = httpRequest.post("/register");
		
		int statusCode = response.getStatusCode();
		AssertJUnit.assertEquals(statusCode, 201);
		String successCode = response.jsonPath().get("SuccessCode");
		AssertJUnit.assertEquals(successCode, "OPERATION_SUCCESS");
	}
	
	@Test(description = "Try to create a duplicate user and verify the result")
	public void test_2() {
		loadRegistrationUrl();
		RequestSpecification httpRequest = RestAssured.given();
		JSONObject requestParams = new JSONObject();
		requestParams.put("FirstName", "RestAPI"+n);
		requestParams.put("LastName", "RestAPI"+n);
		requestParams.put("UserName", "RestAPI"+n);
		requestParams.put("Email", "RestAPI"+n+"@test.com");
		requestParams.put("Password", "1234567890");
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(requestParams.toJSONString());
		Response response = httpRequest.post("/register");
		
		int statusCode = response.getStatusCode();
		String faultId = response.jsonPath().get("FaultId");
		String fault = response.jsonPath().get("fault");
		AssertJUnit.assertEquals(statusCode, 200);
		AssertJUnit.assertEquals(faultId, "User already exists");
		AssertJUnit.assertEquals(fault, "FAULT_USER_ALREADY_EXISTS");
	}
}
