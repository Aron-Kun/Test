Feature: Task C

	Scenario Outline: Verify Categories displayed and highlighted correctly
		Given Accept Privacy Policy
		Then Verify "Home" as default category are highlighted correctly
		When User navigate to "<category>" page
		Then Verify "<category>" as category are highlighted correctly

 Examples: Categories
	| scenario	|	category	|
	|	C2	|	Ocean Rescue	|