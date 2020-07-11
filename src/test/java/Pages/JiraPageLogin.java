package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class JiraPageLogin {

    public JiraPageLogin(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    @FindBy(id = "login-form-username")
    public WebElement usernameField;

    @FindBy(id = "login-form-password")
    public WebElement passwordField;

    @FindBy(xpath = "//input[@value='Log In']")
    public WebElement loginButton;


}
