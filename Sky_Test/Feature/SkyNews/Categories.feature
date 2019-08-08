Feature: Task B

	Scenario Outline: Verify Categories displayed and highlighted correctly
		Given Accept Privacy Policy
		And User clicks on more category button
		Then Verify "<categories>" as categories are displayed correctly		


 Examples: Categories
	| scenario	|	categories	|
	|	B	| Home, UK, World, Politics, US, Ocean Rescue, Science & Tech, Business, Ents & Arts, Offbeat, Analysis, Opinion, Sky Views, Videos, Weather	|