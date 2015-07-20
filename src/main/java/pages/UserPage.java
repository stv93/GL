package pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import other.MethodsForTests;

/**
 * Created by tetiana.sviatska on 7/9/2015.
 */
public class UserPage extends AuthenticationBasePage<UserPage>{

    @FindBy(css = "#main-panel h1")
    private WebElement name;

    private String userName;

    public UserPage(@NotNull WebDriver driver, @NotNull String userName) {
        super(driver);
        this.userName = userName;
    }

    private UserPage(@NotNull WebDriver driver, boolean checkIfLoaded) {
        super(driver, checkIfLoaded);
    }

    public String getName(){
        return name.getText();
    }

    @Override
    public String getPageUrl() {
        return String.format("http://seltr-kbp1-1.synapse.com:8080/user/%s/", MethodsForTests.encode(userName));
    }

    protected static UserPage createUserPageWithLoadingValidation(@NotNull WebDriver driver, @NotNull String userName) {
        return new UserPage(driver, true) {
            @Override
            public String getPageUrl() {
                return String.format("http://seltr-kbp1-1.synapse.com:8080/user/%s/", MethodsForTests.encode(userName));
            }
        };
    }
}
