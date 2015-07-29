package pages;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import other.MethodsForTests;
import other.OwnMatchers;

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

    @Override
    protected void verifyUniqueElement() throws Error {
        Assert.assertTrue(driver.findElements(By.cssSelector("#tasks .task-link")).stream().
                anyMatch(webElement -> webElement.getAttribute("href").endsWith("/delete")));
        Assert.assertThat(By.id("description"), OwnMatchers.presenceOfElementLocatedBy(driver));
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
