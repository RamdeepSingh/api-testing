Feature: Validating random payloads

	@Regression 
  Scenario Outline: Verify the Carbon credits' Promotions
    Given User prepares a base payload to hit an endpoint
    And user adds additional parameters to get final payload
    When final payload hits the endpoint
    Then endpoint results in successful response
    And Name must be "<TitleName>"
    And CanRelist value must be "<CanRelist>"
		And "<Name>" must have description of "<Description>"
	
  Examples:
  	| TitleName 				| CanRelist 	 		| Name  			| Description 							|
  	| Carbon credits		| true		 	    	| Gallery 		| Good position in category	|



		