package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import other.OwnMatchers;

/**
 * Created by tetiana.sviatska on 7/28/2015.
 */
public class ManagePage extends AuthenticationBasePage<ManagePage>{

    @FindBy(css = "a[href=\"configureSecurity\"]")
    private WebElement securityConfiguration;

    @FindBy(css = "a[class=\" \"][href=\"configure\"]")
    private WebElement systemConfiguration;

    protected ManagePage(WebDriver driver, boolean checkIfLoaded) {
        super(driver, checkIfLoaded);
    }

    public ManagePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getPageUrl() {
        return "http://seltr-kbp1-1.synapse.com:8080/manage";
    }

    @Override
    protected void verifyUniqueElement() throws Error {
       Assert.assertThat(securityConfiguration, OwnMatchers.presenceOfElement());
       Assert.assertThat(systemConfiguration, OwnMatchers.presenceOfElement());
    }
}
