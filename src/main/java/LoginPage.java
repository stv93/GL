import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
public class LoginPage extends Page<LoginPage> {

    public static final String LOGIN_PAGE_URL = "http://seltr-kbp1-1.synapse.com:8080//login";

    @FindBy(id = "j_username")
    private WebElement loginLocator;

    @FindBy(name = "j_password")
    private WebElement passwordLocator;

    @FindBy(id = "yui-gen1-button")
    private WebElement signInButton;

    @FindBy(css = "#main-panel-content a[href]")
    private WebElement signUpLink;

    public LoginPage(WebDriver driver){
        super(driver, LOGIN_PAGE_URL);
    }


    public HomePage signIn(String login, String password){
        loginLocator.sendKeys(login);
        passwordLocator.sendKeys(password);
        signInButton.click();
        return new HomePage(driver);
    }

    public LoginErrorPage incorrectSignIn(String login, String password){
        loginLocator.sendKeys(login);
        passwordLocator.sendKeys(password);
        signInButton.click();
        return new LoginErrorPage(driver);
    }

    public SignUpPage signUp(){
        signUpLink.click();
        return new SignUpPage(driver);
    }

}
