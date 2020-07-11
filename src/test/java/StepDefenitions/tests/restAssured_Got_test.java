package StepDefenitions.tests;

import Pojo.jiraCookie.GotPojo;
import Utils.ConfigReader;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class restAssured_Got_test {

    GotPojo parspedResponse;


    @When("user sends a get request on APi to url")
    public void user_sends_a_get_request_on_APi_to_url() {

            parspedResponse = given().header("accept", ContentType.JSON).when().get(ConfigReader.getProperty("Url"))
                    .then().statusCode(200).and().contentType(ContentType.JSON).extract().as(GotPojo.class);



    }

    @Then("the user get all houses and ids and store them in a map")
    public void the_user_get_all_houses_and_ids_and_store_them_in_a_map() {

        Map<String, String> gotProject= new HashMap<>();
        for(int i=0; i<parspedResponse.getData().size(); i++) {
            String gotId = parspedResponse.getData().get(i).get_id();
            String gotHouse = parspedResponse.getData().get(i).getHouse();
            gotProject.put(gotId, gotHouse);
        }

        for(String id : gotProject.keySet())
            System.out.println(id+": "+gotProject.get(id));
    }


}
