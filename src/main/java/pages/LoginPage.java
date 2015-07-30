package pages;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import other.OwnMatchers;

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

    public LoginPage(@NotNull WebDriver driver) {
        super(driver);
    }

    protected LoginPage(@NotNull WebDriver driver, boolean checkIfLoaded) {
        super(driver, checkIfLoaded);
    }

    @NotNull
    public HomePage signIn(@NotNull String login, @NotNull String password) {
        log.info("Logging in as {}", login);
        loginLocator.sendKeys(login);
        passwordLocator.sendKeys(password);
        signInButton.click();
        return new HomePage(driver, true);
    }

    @NotNull
    public LoginErrorPage incorrectSignIn(@Nullable String login, @Nullable String password) {
        log.info("Logging in as {} with incorrect password", login);
        loginLocator.sendKeys(login);
        passwordLocator.sendKeys(password);
        signInButton.click();
        return new LoginErrorPage(driver, true);
    }

    public SignUpPage goToSignUp() {
        signUpLink.click();
        return new SignUpPage(driver, true);
    }

    @Override
    public String getPageUrl() {
        return "http://seltr-kbp1-1.synapse.com:8080/login";
    }

    @Override
    protected void verifyUniqueElement() throws Error {
        Assert.assertThat(loginLocator, OwnMatchers.presenceOfElement());
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        Assert.assertTrue("Not on the right page.", url.contains(getPageUrl()));
    }

}
