import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
public class LoginErrorPage {
    private final WebDriver driver;
    private final String loginErrorPageURL = "http://seltr-kbp1-1.synapse.com:8080/loginError";

    @FindBy(css = "#main-panel-content > div:nth-child(2) > a")
    private WebElement tryAgain;

    public LoginPage tryAgain(){
        tryAgain.click();
        return new LoginPage(driver);
    }

    public LoginErrorPage(WebDriver driver){

        Assert.assertTrue("This is not the page you are expected", driver.getCurrentUrl().equals(loginErrorPageURL));

        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
}
