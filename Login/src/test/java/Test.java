import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
public class Test {
    public static void main(String[] args) {

        String login = "admin";
        String password = "admin";

        WebDriver driver = new FirefoxDriver();

        driver.get("http://seltr-kbp1-1.synapse.com:8080/");

        LoginPage page = new LoginPage(driver);
        HomePage homePage = page.signIn(login, password);
        page = homePage.logout();
        page.incorrectSignIn(login, password + "1");
        /*page = loginErrorPage.tryAgain();
        page.signUp();*/
    }
}
