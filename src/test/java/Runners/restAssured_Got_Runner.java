package Runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/restAssuredGot.feature",
        glue = "StepDefenitions",
        monochrome = false,
        dryRun = false
)

public class restAssured_Got_Runner {
}
