package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
public class HomePage extends Page<HomePage> {

    public static final String HOME_PAGE_URL = "http://seltr-kbp1-1.synapse.com:8080/";

    @FindBy(css = ".login a[href*=\"/logout\"]")
    private WebElement logOut;

    public HomePage(WebDriver driver) {
        super(driver, HOME_PAGE_URL);
    }

    public LoginPage logout() {
        logOut.click();
        return new LoginPage(driver);
    }

}
