package com.sky.cucumberTest;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		//	features =  "Feature/SkyNews/PageTitle.feature" //Task A		
		//	features =  "Feature/SkyNews/Categories.feature" //Task B
		//	features =  "Feature/SkyNews/NavigationFocus.feature" //Task C
			features =  "Feature/SkyNews/MainTitle.feature" //Task D
		
		, glue = { "com.sky.stepDefinition" }
	    //,dryRun = true
		, monochrome = true)

public class NonJenkinsRunner {

}
 