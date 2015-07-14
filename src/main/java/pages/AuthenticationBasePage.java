package pages;

import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import other.CredentialsProvider;


/**
 * Created by tetiana.sviatska on 7/13/2015.
 */
public abstract class AuthenticationBasePage<T extends AuthenticationBasePage<T>> extends Page<T> {

    @FindBy(css = ".login a[href*=\"/logout\"]")
    private WebElement logOut;

    protected AuthenticationBasePage(WebDriver driver, String pageUrl) {
        super(driver, pageUrl);
    }

    @Override
    protected void load() {
        try {
            if (!isLoggedIn()) {
                new LoginPage(driver).get().signIn(CredentialsProvider.getDefaultLogin(), CredentialsProvider
                        .getDefaultPassword());
            }
        } catch (Error e) {
            // This should mean we already logged in
        }
        super.load();
    }

    @Override
    protected void isLoaded() throws Error {
        Assert.assertTrue("Log out button is not displayed", isLoggedIn());
        super.isLoaded();
    }

    protected boolean isLoggedIn() {
        try {
            return logOut.isDisplayed();
        } catch (NoSuchElementException e) {
        }
        return false;
    }

    public LoginPage logout() {
        logOut.click();
        return new LoginPage(driver);
    }
}
