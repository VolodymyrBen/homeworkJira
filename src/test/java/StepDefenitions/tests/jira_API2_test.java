package StepDefenitions.tests;

import Pages.HomePage;
import Pages.JiraPageLogin;
import Pojo.jiraCookie.AssigneePojo;
import Utils.BrowserUtils;
import Utils.ConfigReader;
import Utils.Driver;
import Utils.PayloadUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class jira_API2_test {

    WebDriver driver = Driver.getDriver();
    JiraPageLogin jiraPageLogin = new JiraPageLogin(driver);
    HomePage homePage = new HomePage(driver);

    int expectedTotalStories;

    @When("the user sends get request to get total number of issues assigned to assignee")
    public void the_user_sends_get_request_to_get_total_number_of_issues_assigned_to_assignee() throws URISyntaxException, IOException {

        HttpClient client = HttpClientBuilder.create().build();

        // http://localhost:8080/rest/api/2/search?jql=assignee=Roman
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("http").setHost("localhost:8080").setPath("rest/api/2/search").setCustomQuery("jql=assignee=Roman");

        HttpGet get = new HttpGet(uriBuilder.build());
        get.setHeader("Accept", "application/json");
        get.setHeader("Content-Type", "application/json");
        get.setHeader("Cookie", PayloadUtil.getJsessionCookie());


        HttpResponse response = client.execute(get);

        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        AssigneePojo parseResponse = objectMapper.readValue(response.getEntity().getContent(), AssigneePojo.class);

        Assert.assertEquals(parseResponse.getTotal(),parseResponse.getIssues().size());

        expectedTotalStories= parseResponse.getTotal();

        System.out.println(expectedTotalStories);
    }

    @Then("the user validates total number of stories for same assignee on IU")
    public void the_user_validates_total_number_of_stories_for_same_assignee_on_IU() throws InterruptedException {

        BrowserUtils.navigateUrl(driver,ConfigReader.getProperty("loginUrl1"));

        homePage.userStorySearchButton.sendKeys("Roman");

        Thread.sleep(1000);
        int actualTotalStories = homePage.issueContent.size();


        Assert.assertEquals(expectedTotalStories,actualTotalStories);

    }


}
