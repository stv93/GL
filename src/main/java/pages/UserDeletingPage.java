package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by tetiana.sviatska on 7/7/2015.
 */
public class UserDeletingPage extends AuthenticationBasePage<UserDeletingPage> {

    public static String DELETE_PAGE_FORMAT = "http://seltr-kbp1-1.synapse.com:8080/user/%s/delete";

    @FindBy(id = "yui-gen1-button")
    private WebElement deleteButton;

    public UserDeletingPage(WebDriver driver, String userName) {
        super(driver, String.format(DELETE_PAGE_FORMAT, userName));
    }

    public HomePage deleteUser(){
        deleteButton.click();
        return new HomePage(driver);
    }

}
