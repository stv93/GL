package pages;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
public class LoginPage extends Page<LoginPage> {

    @FindBy(id = "j_username")
    private WebElement loginLocator;

    @FindBy(name = "j_password")
    private WebElement passwordLocator;

    @FindBy(id = "yui-gen1-button")
    private WebElement signInButton;

    @FindBy(css = "#main-panel-content a[href]")
    private WebElement signUpLink;

    @Override
    public String getPageUrl() {
        return "http://seltr-kbp1-1.synapse.com:8080/login";
    }

    public LoginPage(@NotNull WebDriver driver) {
        super(driver);
    }

    protected LoginPage(@NotNull WebDriver driver, boolean checkIfLoaded) {
        super(driver, checkIfLoaded);
    }

    @NotNull
    public HomePage signIn(@NotNull String login, @NotNull String password) {
        loginLocator.sendKeys(login);
        passwordLocator.sendKeys(password);
        signInButton.click();
        if(!isLoggedIn()){
            throw new Error("Entered login and password are incorrect");
        }
        return new HomePage(driver, true);
    }

    @NotNull
    public LoginErrorPage incorrectSignIn(@Nullable String login, @Nullable String password) {
        loginLocator.sendKeys(login);
        passwordLocator.sendKeys(password);
        signInButton.click();
        if(isLoggedIn()){
            throw new Error("Entered login and password are correct");
        }
        return new LoginErrorPage(driver, true);
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        Assert.assertTrue("Not on the right page.", url.contains(getPageUrl()));
    }

    public SignUpPage signUp() {
        signUpLink.click();
        return new SignUpPage(driver, true);
    }

}
