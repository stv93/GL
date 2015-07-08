package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by tetiana.sviatska on 7/7/2015.
 */
public class UserDeletingPage extends Page<UserDeletingPage> {

    @FindBy(id = "yui-gen1-button")
    private WebElement deleteButton;

    static String UserDeletingPageUrlBeforeName = "http://seltr-kbp1-1.synapse.com:8080/user/";
    static String UserDeletingPageUrlAfterName = "/delete";

    public UserDeletingPage(WebDriver driver, String userName) {
        super(driver, UserDeletingPageUrlBeforeName + userName + UserDeletingPageUrlAfterName);
    }

    public void deleteUser(){
        deleteButton.click();
    }

}
