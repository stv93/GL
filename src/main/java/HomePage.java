import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
public class HomePage extends LoadableComponent<HomePage> {
    private final WebDriver driver;

    @FindBy(css = "#header > div.login > span > a:nth-child(2)")
    private WebElement logOut;

    public HomePage(WebDriver driver){
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
        Assert.assertTrue("Not on the right page.", url.equals("http://seltr-kbp1-1.synapse.com:8080/"));
        Assert.assertTrue(ExpectedConditions.titleIs("ИнфоПанель [Jenkins]").apply(driver));
    }

    public LoginPage logout(){
        logOut.click();
        return new LoginPage(driver);
    }
}
