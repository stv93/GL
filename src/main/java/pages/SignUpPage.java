package pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
public class SignUpPage extends Page<SignUpPage> {

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

    @Override
    public String getPageUrl() {
        return "http://seltr-kbp1-1.synapse.com:8080/signup";
    }

    public SignUpPage(@NotNull WebDriver driver) {
        super(driver);
    }

    public SignUpResultPage signUp(String username, String password, String confirmPassword, String fullname, String
            email) {
        log.info("Signing up with: (User: {}, Pass: {}, Confirm pass: {}, Name: {}, Email: {})", username, password,
                confirmPassword, fullname, email);
        userName.sendKeys(username);
        this.password.sendKeys(password);
        this.confirmPassword.sendKeys(confirmPassword);
        this.fullname.sendKeys(fullname);
        this.email.sendKeys(email);
        signUpButton.click();
        return new SignUpResultPage(driver);
    }
}
