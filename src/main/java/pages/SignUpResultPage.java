package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by tetiana.sviatska on 7/8/2015.
 */
public class SignUpResultPage extends Page<SignUpResultPage> {

    @FindBy(css = ".error")
    private WebElement error;

    @FindBy(css = "#main-panel-content h1")
    private WebElement message;

    public SignUpResultPage(WebDriver driver) {
        super(driver, "http://seltr-kbp1-1.synapse.com:8080/securityRealm/createAccount");
    }

    public String getErrorText() {
        return error.getText();
    }

    public String getMessageText(){
        return message.getText();
    }
}
