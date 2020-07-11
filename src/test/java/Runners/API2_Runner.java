package Runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/jiraAPI2.feature",
        glue = "StepDefenitions",
        monochrome = false,
        dryRun = false
)

public class API2_Runner {
}
