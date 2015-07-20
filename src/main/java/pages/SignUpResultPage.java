package pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by tetiana.sviatska on 7/8/2015.
 */
public class SignUpResultPage extends Page<SignUpResultPage> {

    @FindBy(css = ".error")
    private WebElement error;

    @FindBy(css = "#main-panel h1")
    private WebElement message;

    @Override
    public String getPageUrl() {
        return "http://seltr-kbp1-1.synapse.com:8080/securityRealm/createAccount";
    }

    public SignUpResultPage(@NotNull WebDriver driver) {
        super(driver);
    }

    protected SignUpResultPage(@NotNull WebDriver driver, boolean checkIfLoaded) {
        super(driver, checkIfLoaded);
    }

    public String getErrorText() {
        if(isLoggedIn()){
            return null;
        }
        return error.getText();
    }

    public String getMessageText(){
        return message.getText();
    }
}
