package pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by tetiana.sviatska on 7/7/2015.
 */
public class UserDeletingPage extends AuthenticationBasePage<UserDeletingPage> {

    @FindBy(id = "yui-gen1-button")
    private WebElement deleteButton;

    private String userName;

    public UserDeletingPage(@NotNull WebDriver driver, @NotNull String userName) {
        super(driver);
        this.userName = userName;
    }

    public HomePage deleteUser(){
        deleteButton.click();
        return new HomePage(driver);
    }

    @Override
    public String getPageUrl() {
        return String.format("http://seltr-kbp1-1.synapse.com:8080/user/%s/delete", userName);
    }
}
