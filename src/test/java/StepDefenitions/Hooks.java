package StepDefenitions;

import Utils.BrowserUtils;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

public class Hooks {


    @After
    public void tearDown(Scenario scenario){

        if (scenario.isFailed()){
            System.out.println("--> is failed");
            BrowserUtils.takeScreenShot();
        }
    }

}
