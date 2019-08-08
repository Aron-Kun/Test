Feature: Task D
	
	Scenario Outline: Task A - Verify tab's tittle 
		Given Accept Privacy Policy
		When User clicks on "<article>" 
		Then Verify "<article>" article's title displayed as "<title>" correctly
#Few example
 Examples: Titles
	| scenario	|	article	|	title	|	
	| D	|More woe for British holidaymakers as Ryanair pilots announce strike dates|More woe for British holidaymakers as Ryanair pilots announce strike dates|