package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by tetiana.sviatska on 7/9/2015.
 */
public class UserPage extends AuthenticationBasePage<UserPage>{
    private static String USER_NAME_URL = "http://seltr-kbp1-1.synapse.com:8080/user/%s/";

    @FindBy(css = "#main-panel-content h1")
    private WebElement name;

    public UserPage(WebDriver driver, String userName) {
        super(driver, String.format(USER_NAME_URL, userName));
    }

    public String getName(){
        return name.getText();
    }

}
