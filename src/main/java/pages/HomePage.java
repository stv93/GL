package pages;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
public class HomePage extends AuthenticationBasePage<HomePage> {

    @FindBy(css = "#projectstatus a.model-link:not([href*=\"last\"])")
    private List<WebElement> projects;

    @FindBy(css = ".inverse")
    private WebElement userLink;

    @FindBy(css = "p.error")
    private WebElement error;

    @FindBy(className = "tabBar")
    private WebElement views;

    @Override
    public String getPageUrl() {
        return "http://seltr-kbp1-1.synapse.com:8080/";
    }

    protected HomePage(@NotNull WebDriver driver, boolean checkIfLoaded) {
        super(driver, checkIfLoaded);
    }

    @Override
    protected void verifyUniqueElement() throws Error {
        Assert.assertTrue(isElementPresent(views) || isElementPresent(error));
    }

    public HomePage(@NotNull WebDriver driver) {
        super(driver);
    }
}
