package pages;

import org.jetbrains.annotations.NotNull;
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

    @Override
    public String getPageUrl() {
        return "http://seltr-kbp1-1.synapse.com:8080/";
    }

    public HomePage(@NotNull WebDriver driver) {
        super(driver);
    }
}
