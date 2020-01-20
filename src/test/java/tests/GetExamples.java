package tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.with;
import java.io.File;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import actions.ActionsClass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetExamples extends ActionsClass {

	@Test(description = "Get users list and validate the result")
	public void test_01() {
		loadApiUrl();
		Response response = given().request(Method.GET, "/users");
		JsonPath jsonPathEvaluator = getJsonPath(response);
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		AssertJUnit.assertEquals(response.then().contentType(ContentType.JSON).extract().path("[0].address.suite"),
				"Apt. 556");
		AssertJUnit.assertEquals(jsonPathEvaluator.get("[1].address.suite"), "Suite 879");
		AssertJUnit.assertEquals(jsonPathEvaluator.get("[0].company.catchPhrase"), "Multi-layered client-server neural-net");
	}

	@Test(description = "Get posts list and validate the result")
	public void test_02() {
		loadApiUrl();
		Response response = given().request(Method.GET, "/posts");
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		given().get("/posts").then().body("[0].title",
				equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"));
	}

	@Test(description = "Send invalid request and validate the status code")
	public void test_03() {
		loadApiUrl();
		Response response = given().request(Method.GET, "/album");
		AssertJUnit.assertEquals(response.getStatusCode(), 404);
	}

}
