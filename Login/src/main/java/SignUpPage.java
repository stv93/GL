import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
public class SignUpPage {
    private final WebDriver driver;
    private final String signUpPageURL = "http://seltr-kbp1-1.synapse.com:8080/signup";

    public SignUpPage(WebDriver driver){

        Assert.assertTrue("This is not the page you are expected", driver.getCurrentUrl().equals(signUpPageURL));

        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
}
