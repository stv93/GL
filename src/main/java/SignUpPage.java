import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
public class SignUpPage extends Page<SignUpPage> {

    public static final String SIGN_UP_PAGE_URL = "http://seltr-kbp1-1.synapse.com:8080/signup";
    public String errorTextExpected;

    @FindBy(id = "username")
    private WebElement userName;

    @FindBy(name = "password1")
    private WebElement password;

    @FindBy(name = "password2")
    private WebElement confirmPassword;

    @FindBy(name = "fullname")
    private WebElement fullname;

    @FindBy(name = "email")
    private WebElement email;

    @FindBy(id = "yui-gen1-button")
    private WebElement signUpButton;

    @FindBy(css = ".error")
    private WebElement error;

    public SignUpPage(WebDriver driver){
        super(driver, SIGN_UP_PAGE_URL);
    }

    public SignUpPage signUp(String username, String password, String confirmPassword, String fullname, String email){
        userName.sendKeys(username);
        this.password.sendKeys(password);
        this.confirmPassword.sendKeys(confirmPassword);
        this.fullname.sendKeys(fullname);
        this.email.sendKeys(email);
        signUpButton.click();
        return new SignUpPage(driver);
    }

    public void clearFields(){
        userName.clear();
        password.clear();
        confirmPassword.clear();
        fullname.clear();
        email.clear();
    }

    public String getErrorText(){
        return error.getText();
    }
}
