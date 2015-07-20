package pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
public class LoginErrorPage extends Page<LoginErrorPage> {

    @FindBy(css = "#main-panel a[href*='login?from']")
    private WebElement tryAgain;

    @Override
    public String getPageUrl() {
        return "http://seltr-kbp1-1.synapse.com:8080/loginError";
    }

    public LoginErrorPage(@NotNull WebDriver driver) {
        super(driver);
    }

    protected LoginErrorPage(@NotNull WebDriver driver, boolean checkIfLoaded) {
        super(driver, checkIfLoaded);
    }

    public LoginPage tryAgain() {
        tryAgain.click();
        return new LoginPage(driver, true);
    }
}
