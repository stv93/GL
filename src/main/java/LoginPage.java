import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Objects;

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

    public HomePage signIn(@NotNull String login, @NotNull String password){
        loginLocator.sendKeys(login);
        passwordLocator.sendKeys(password);
        signInButton.click();
        return new HomePage(driver);
    }

    @NotNull
    public LoginErrorPage incorrectSignIn(@Nullable String login, @Nullable String password){
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
