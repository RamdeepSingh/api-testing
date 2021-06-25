package stepDefinitions;

import java.io.IOException;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class StepDef extends Utils {

	ResponseSpecification resspec;
	RequestSpecification res;
	Response response;
	TestDataBuild data = new TestDataBuild();
	JsonPath js;
	public static String place_id;

	@Given("Add place payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {

		this.res = given().spec(requestSpecification()).body(data.addPlacePayload(name, language, address));

	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String httpMethod) {

		System.out.println(resource + " " + httpMethod);
		APIResources resourceAPI = APIResources.valueOf(resource);

		this.resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		if (httpMethod.equalsIgnoreCase("POST")) {
			this.response = res.when().post(resourceAPI.getResource());
		} else if (httpMethod.equalsIgnoreCase("DELETE")) {
			this.response = res.when().delete(resourceAPI.getResource());
		} else if (httpMethod.equalsIgnoreCase("GET")) {
			this.response = res.when().get(resourceAPI.getResource());
		}
	}

	@Then("the API call got success with status code 200")
	public void the_api_call_got_success_with_status_code() {

		assertEquals(response.getStatusCode(), 200);

	}

	@Then("{string} in response body is {string}")
	public void status_in_response_body_is_ok(String keyValue, String expectedValue) {

		assertEquals(getJsonPath(response, keyValue), expectedValue);

	}

	@Then("verify place_id created matches to {string} using {string}")
	public void verify_place_id_created_matches_to_using(String expectedName, String resource) throws IOException {

		StepDef.place_id = getJsonPath(response, "place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", StepDef.place_id);
		user_calls_with_http_request(resource, "GET");
		String actualName = getJsonPath(response, "name");
		assertEquals(actualName, expectedName);

	}

	@Given("DeletePlace payload")
	public void delete_place_payload() throws IOException {

		this.res = given().spec(requestSpecification()).body(data.deletePlacePayload(StepDef.place_id));
		
	}
}
