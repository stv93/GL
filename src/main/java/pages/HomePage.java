package pages;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import other.OwnMatchers;

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

    @FindBy(css = ".tabBar")
    private WebElement views;

    @FindBy(css = ".tab.active")
    private  WebElement activeView;

    public HomePage(@NotNull WebDriver driver) {
        super(driver);
    }

    protected HomePage(@NotNull WebDriver driver, boolean checkIfLoaded) {
        super(driver, checkIfLoaded);
    }

    @Override
    public String getPageUrl() {
        return "http://seltr-kbp1-1.synapse.com:8080/";
    }

    public String getDefaulViewName(){
        return activeView.getText();
    }

    @Override
    protected void verifyUniqueElement() throws Error {
        Assert.assertThat(new WebElement[]{views, error}, OwnMatchers.presenceAnyOfElements());
    }
}
