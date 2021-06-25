Feature: Validating Place APIs

	@AddPlace @Regression
  Scenario Outline: Verify if place is being added successfully using AddPlaceAPI
    Given Add place payload with "<name>" "<language>" "<address>"
    When user calls "AddPlaceAPI" with "post" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"  

  Examples:
  	|name 				| language 	  | address 											|
  	|Frank Buffey | French 	    | 29, Weston Meadow Footfront		|
  	|Joe Tribbiani| Italian			| 14, Scotland West Hill 				|

	@GetPlace @Regression
	Scenario Outline: Verify if getPlaceAPI is fetching correct information as given while hitting AddPlaceAPI
    Given Add place payload with "<name>" "<language>" "<address>"
    When user calls "AddPlaceAPI" with "post" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"  
		And verify place_id created matches to "<name>" using "GetPlaceAPI"
    
  Examples:
  	|name 				| language 	  | address 											|
  	|Frank Buffey | French 	    | 29, Weston Meadow Footfront		|
#  	|Joe Tribbiani| Italian			| 14, Scotland West Hill 				|


	@DeletePlace @Regression
	Scenario Outline: Verify if delete place functionality is working fine
		Given DeletePlace payload
		When user calls "DeletePlaceAPI" with "DELETE" http request
		Then the API call got success with status code 200
		And "status" in response body is "OK"
		
		