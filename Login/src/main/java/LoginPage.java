import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
public class LoginPage {

    private final WebDriver driver;

    @FindBy(id = "j_username")
    private WebElement loginLocator;

    @FindBy(name = "j_password")
    private WebElement passwordLocator;

    @FindBy(id = "yui-gen1-button")
    private WebElement signInButtton;

    @FindBy(linkText = "signup")
    private WebElement signUpLink;

    public HomePage signIn(String login, String password){
        setLogin(login);
        setPassword(password);
        signInButtton.click();
        return new HomePage(driver);
    }

    public LoginErrorPage incorrectSignIn(String login, String password){
        setLogin(login);
        setPassword(password);
        signInButtton.click();
        return new LoginErrorPage(driver);
    }

    public SignUpPage signUp(){
        signUpLink.click();
        return new SignUpPage(driver);
    }

    private LoginPage setLogin(String login){
        loginLocator.sendKeys(login);
        return this;
    }
    private LoginPage setPassword(String password){
        passwordLocator.sendKeys(password);
        return this;
    }

    public LoginPage(WebDriver driver){

        PageFactory.initElements(driver,this);
        this.driver = driver;
    }
}
