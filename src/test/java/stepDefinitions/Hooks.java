package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		// It will give us place_id
		StepDef sd = new StepDef();
		if (StepDef.place_id == null) {
			sd.add_place_payload_with("Singh", "Punjabi", "Punjab");
			sd.user_calls_with_http_request("AddPlaceAPI", "POST");
			sd.verify_place_id_created_matches_to_using("Singh", "GetPlaceAPI");
		}
	}

}
