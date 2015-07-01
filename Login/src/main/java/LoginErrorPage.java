import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
public class LoginErrorPage extends LoadableComponent<LoginErrorPage> {
    private final WebDriver driver;

    @FindBy(css = "#main-panel-content > div:nth-child(2) > a")
    private WebElement tryAgain;

    public LoginErrorPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @Override
    protected void load() {
        driver.get("http://seltr-kbp1-1.synapse.com:8080/");
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        Assert.assertTrue("Not on the right page.", url.equals("http://seltr-kbp1-1.synapse.com:8080/loginError"));
    }

    public LoginPage tryAgain(){
        tryAgain.click();
        return new LoginPage(driver);
    }
}
