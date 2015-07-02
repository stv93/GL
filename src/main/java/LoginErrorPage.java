import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
public class LoginErrorPage extends Page<LoginErrorPage>{

    public static final String LOGIN_ERROR_PAGE_URL = "http://seltr-kbp1-1.synapse.com:8080/loginError";

    @FindBy(css = "#main-panel-content > div:nth-child(2) > a")
    private WebElement tryAgain;

    public LoginErrorPage(WebDriver driver){
        super(driver,LOGIN_ERROR_PAGE_URL);
    }

    public LoginPage tryAgain(){
        tryAgain.click();
        return new LoginPage(driver);
    }
}
