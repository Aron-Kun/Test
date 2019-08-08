Feature: Task A 
	
	Scenario Outline: Task A - Verify tab's tittle 
		Given Accept Privacy Policy
		When User navigate to "<category>" page
		Then Verify "<title>" as tabs title displayed correctly
#Few example
 Examples: Titles
	| scenario	|	category	|	title	|	
	|	A	|	Home	|	The Latest News from the UK and Around the World	|