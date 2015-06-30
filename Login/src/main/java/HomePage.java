import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
public class HomePage {
    private final WebDriver driver;
    private final String homePageURL = "http://seltr-kbp1-1.synapse.com:8080/";

    @FindBy(css = "#header > div.login > span > a:nth-child(2)")
    private WebElement logOut;

    public LoginPage logout(){
        logOut.click();
        return new LoginPage(driver);
    }

    public HomePage(WebDriver driver){

            Assert.assertTrue("This is not the page you are expected", driver.getCurrentUrl().equals(homePageURL));

        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
}
