import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
public class SignUpPage extends LoadableComponent<SignUpPage> {
    private final WebDriver driver;

    public SignUpPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @Override
    protected void load() {
        driver.get("http://seltr-kbp1-1.synapse.com:8080/signup");
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        Assert.assertTrue("Not on the right page.", url.equals("http://seltr-kbp1-1.synapse.com:8080/signup"));
    }
}
