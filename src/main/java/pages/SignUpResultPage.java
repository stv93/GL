package pages;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import other.OwnMatchers;

/**
 * Created by tetiana.sviatska on 7/8/2015.
 */
public class SignUpResultPage extends Page<SignUpResultPage> {

    @FindBy(css = "div.error")
    private WebElement error;

    @FindBy(css = "#main-panel h1")
    private WebElement message;

    @FindBy(css = "a[href=\"..\"]")
    private WebElement toTheHomePage;

    @Override
    public String getPageUrl() {
        return "http://seltr-kbp1-1.synapse.com:8080/securityRealm/createAccount";
    }

    protected SignUpResultPage(@NotNull WebDriver driver, boolean checkIfLoaded) {
        super(driver, checkIfLoaded);
    }

    @Override
    protected void verifyUniqueElement() throws Error {
        Assert.assertThat(new WebElement[]{toTheHomePage, error}, OwnMatchers.presenceAnyOfElements());
    }

    public String getErrorText() {
        if(isElementPresent(toTheHomePage)){
            return null;
        }
        return error.getText();
    }

    public String getMessageText(){
        return message.getText();
    }

}
