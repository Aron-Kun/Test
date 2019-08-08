package com.sky.cucumberTest;
 
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty:target/cucumber-pretty.txt", "html:target/cucumber-html-report"},
		features = "Feature"
		,glue={"com.sky.stepDefinition"}
		//,dryRun = true
		,monochrome = true
		)
 
public class TestRunner {

}
