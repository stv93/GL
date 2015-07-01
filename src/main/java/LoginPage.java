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
public class LoginPage extends LoadableComponent<LoginPage> {

    private final WebDriver driver;

    @FindBy(id = "j_username")
    private WebElement loginLocator;

    @FindBy(name = "j_password")
    private WebElement passwordLocator;

    @FindBy(id = "yui-gen1-button")
    private WebElement signInButtton;

    @FindBy(css = "#main-panel-content > div > div > a")
    private WebElement signUpLink;

    public LoginPage(WebDriver driver){

        PageFactory.initElements(driver,this);
        this.driver = driver;
    }

    @Override
    protected void load() {
        driver.get("http://seltr-kbp1-1.synapse.com:8080/");
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        Assert.assertTrue("Not on the right page.", url.endsWith("login?from=%2F"));
        Assert.assertTrue(ExpectedConditions.titleIs("Jenkins").apply(driver));
    }

    public HomePage signIn(String login, String password){
        loginLocator.sendKeys(login);
        passwordLocator.sendKeys(password);
        signInButtton.click();
        return new HomePage(driver);
    }

    public LoginErrorPage incorrectSignIn(String login, String password){
        loginLocator.sendKeys(login);
        passwordLocator.sendKeys(password);
        signInButtton.click();
        return new LoginErrorPage(driver);
    }

    public SignUpPage signUp(){
        signUpLink.click();
        return new SignUpPage(driver);
    }

}
