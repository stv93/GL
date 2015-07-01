import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
public class Tests {
    private WebDriver driver;

    @Before
    public  void before(){
        driver = new FirefoxDriver();
    }

    @After
    public void after(){
        driver.close();
    }

    @Test
    public void test() {
        String login = "admin";
        String password = "admin";

        LoginPage page = new LoginPage(driver).get();
        HomePage homePage = page.signIn(login, password).get();
        page = homePage.logout().get();
        LoginErrorPage loginErrorPage = page.incorrectSignIn(login, password + "1").get();
        page = loginErrorPage.tryAgain().get();
        page.signUp();
    }


}
