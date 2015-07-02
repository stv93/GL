import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
@RunWith(Parameterized.class)
public class Tests {
    private WebDriver driver;

    @Before
    public void before() {
        String browser = System.getProperty("browser");
        if (browser.equals("firefox")) {
            driver = new FirefoxDriver();
        }
        else if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\ChromeDriver\\chromedriver.exe");
            driver = new ChromeDriver();
        }
        else throw new IllegalStateException("No matching browser type found");
    }
    /*private String browser;

    public Tests(String browser){
        this.browser = browser;
    }

    @Parameters
    public static Collection<Object[]> browsers(){
        return Arrays.asList(new Object[][]{{"chrome"}, {"firefox"}});
    }


    @Before

    public  void before() {
        getDriver();
    }


    private WebDriver getDriver() {
        switch (browser) {
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "chrome":
                driver = new ChromeDriver();
                break;
            default:
                throw new IllegalStateException("No matching browser type found");
        }
        return driver;
    }*/

    @After
    public void after(){
        driver.quit();
    }

    @Test
    public void test() {
        String login = "admin";
        String password = "admin";

        LoginPage page = new LoginPage(driver).get();
        HomePage homePage = page.signIn(login, password);
        page = homePage.logout();
        LoginErrorPage loginErrorPage = page.incorrectSignIn(login, password + "1");
        page = loginErrorPage.tryAgain();
        page.signUp();
    }


}
