import org.openqa.selenium.WebDriver;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
public class SignUpPage extends Page<SignUpPage> {

    public static final String SIGN_UP_PAGE_URL = "http://seltr-kbp1-1.synapse.com:8080/signup";

    public SignUpPage(WebDriver driver){
        super(driver, SIGN_UP_PAGE_URL);
    }
}
