package pages;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import other.MethodsForTests;

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

    public UserDeletingPage(@NotNull WebDriver driver, boolean checkIfLoaded) {
        super(driver, checkIfLoaded);
    }

    @Override
    protected void verifyUniqueElement() throws Error {
        Assert.assertTrue(isElementPresent(driver.findElement(By.cssSelector("form[name=\"delete\"]"))));
    }

    public HomePage deleteUser(){
        deleteButton.click();
        return new HomePage(driver, true);
    }

    @Override
    public String getPageUrl() {
        return String.format("http://seltr-kbp1-1.synapse.com:8080/user/%s/delete", MethodsForTests.encode(userName));
    }

    /*protected static UserDeletingPage createUserDeletingPageWithLoadingValidation(@NotNull WebDriver driver, @NotNull String userName) {
        return new UserDeletingPage (driver, true) {
            @Override
            public String getPageUrl() {
                return String.format("http://seltr-kbp1-1.synapse.com:8080/user/%s/delete", MethodsForTests.encode(userName));
            }
        };
    }*/
}
