package StepDefenitions.tests;

import Pages.HomePage;
import Pages.JiraPageLogin;
import Utils.BrowserUtils;
import Utils.ConfigReader;
import Utils.Driver;
import Utils.PayloadUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class jira_API_test {

    WebDriver driver = Driver.getDriver();
    JiraPageLogin jiraPageLogin = new JiraPageLogin(driver);
    HomePage homePage = new HomePage(driver);


     String expectedID;

    @When("create user story via API with  {string}, {string}, {string}")
    public void createUserStoryViaAPIWith(String summary, String description, String bug) throws URISyntaxException, IOException {


        HttpClient client = HttpClientBuilder.create().build();

        // http://localhost:8080/rest/api/2/issue
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("http").setHost("localhost:8080").setPath("rest/api/2/issue");

        HttpPost post = new HttpPost(uriBuilder.build());
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Cookie", PayloadUtil.getJsessionCookie());

        HttpEntity entity = new StringEntity(PayloadUtil.getJiraIssuePayload(summary, description, bug));
        post.setEntity(entity);

        HttpResponse response = client.execute(post);

        Assert.assertEquals(HttpStatus.SC_CREATED, response.getStatusLine().getStatusCode());

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String,String> parseResponse = objectMapper.readValue(response.getEntity().getContent(),
                new TypeReference<Map<String,String>>() {});


        expectedID = parseResponse.get("key");

        System.out.println(expectedID+"-> expected ID API");
    }

    @Then("verify user stories in UI {string}, {string}, {string}")
    public void verifyUserStoriesInUI(String expectedSummary, String expectedDescription, String expecteIssueType) throws InterruptedException {


        BrowserUtils.navigateUrl(driver,ConfigReader.getProperty("loginUrl"));



        driver.navigate().refresh();

        driver.switchTo().frame(homePage.iframe);

        homePage.clickDetailButton.click();

        driver.switchTo().parentFrame();

        Assert.assertEquals(expectedID,homePage.actualID.getText().trim());
        Assert.assertEquals(expectedSummary,homePage.actualSummary.getText().trim());
        Assert.assertEquals(expectedDescription,homePage.actualDescription.getText().trim());
        Assert.assertEquals(expecteIssueType,homePage.actualIssueType.getText().trim());

        System.out.println(homePage.actualID.getText().trim()+"-> actual ID from UI");

        driver.navigate().back();


    }

}
