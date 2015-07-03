import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
//@RunWith(Parameterized.class)
public class Tests {
    private WebDriver driver;
    /*private String browser;

    public Tests(String browser){
        this.browser = browser;
    }

    @Parameters
    public static Collection<Object[]> browsers(){
        return Arrays.asList(new Object[][]{{"chrome"}, {"firefox"}});
    }*/

    @Before
    public  void before() {
        getDriver();
    }
    private WebDriver getDriver() {
            String browser = System.getProperty("browser");
            switch (browser.toLowerCase()) {
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                case "chrome":
                    System.setProperty("webdriver.chrome.driver", "ChromeDriver\\chromedriver.exe");
                    driver = new ChromeDriver();
                    break;
                case "internetexplorer":
                    System.setProperty("webdriver.ie.driver", "IEDriver\\IEDriverServer.exe");
                    driver = new InternetExplorerDriver();
                    break;
                default:
                    throw new IllegalStateException("No matching browser type found");
            }
            return driver;
    }

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
