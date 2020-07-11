package Utils;


import Pojo.jiraCookie.JiraPojoCookie;
import Pojo.jiraCookie.JiraSetBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PayloadUtil {

    public static String getPetPayload(int id,String name,String status){
        return "{\n" +
                "  \"id\": "+id+",\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \""+name+"\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \""+status+"\"\n" +
                "}";
    }

    public static String generateStringFromResource(String path) throws IOException {
        String petPayload = new String(Files.readAllBytes(Paths.get(path)));

        return petPayload;
    }

    public static String getJiraIssuePayload(String summary,String description, String issueType){
        return "{\n" +
                "    \"fields\": {\n" +
                "        \"project\":{\n" +
                "            \"key\": \"US\"\n" +
                "        },\n" +
                "        \"summary\": \""+summary+"\",\n" +
                "        \"description\": \""+description+"\",\n" +
                "        \"issuetype\": {\n" +
                "            \"name\": \""+issueType+"\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }

    public static String getJsessionCookie() throws URISyntaxException, IOException {

        HttpClient client = HttpClientBuilder.create().build();

        //  http://localhost:8080/rest/auth/1/session
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("http").setHost("localhost:8080").setPath("rest/auth/1/session");

        HttpPost post = new HttpPost(uriBuilder.build());
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-Type", "application/json");


        JiraSetBody jiraSetBody = new JiraSetBody(ConfigReader.getProperty("userName"), ConfigReader.getProperty("password"));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("target/jira1.json"), jiraSetBody);

        String jira1Payload = PayloadUtil.generateStringFromResource("target/jira1.json");

        HttpEntity entity = new StringEntity(jira1Payload);
        post.setEntity(entity);

        HttpResponse response = client.execute(post);

        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        JiraPojoCookie jiraPojoCookie = objectMapper.readValue(response.getEntity().getContent(), JiraPojoCookie.class);

        String cookieName = jiraPojoCookie.getSession().get("name");
        String cookieValue = jiraPojoCookie.getSession().get("value");

        return String.format("%s=%s",cookieName, cookieValue);
    }


}
