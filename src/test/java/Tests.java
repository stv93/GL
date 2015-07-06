import org.apache.commons.lang.RandomStringUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
//@RunWith(Parameterized.class)
public class Tests {
    public static WebDriver driver;
    public static SignUpPage signUpPage;

    String correctName = RandomStringUtils.randomAscii(20);
    String correctEmail = RandomStringUtils.randomAscii(5)+"@";
    String incorrectEmail = RandomStringUtils.randomNumeric(5);
    String password = RandomStringUtils.randomAscii(6);
    String incorrectConfirmPassword = RandomStringUtils.randomAscii(6);

    /*private String browser;

    public Tests(String browser){
        this.browser = browser;
    }

    @Parameters
    public static Collection<Object[]> browsers(){
        return Arrays.asList(new Object[][]{{"chrome"}, {"firefox"}});
    }*/

    @BeforeClass
    public static void beforeForAll() {
        driver = getDriver();
    }

    @AfterClass
    public static  void afterForAll(){
        driver.quit();
    }

    @Before
    public void before(){
        signUpPage = new SignUpPage(driver).get();
    }


    @Test
    public void nameIsTakenError() {
        signUpPage.signUp(takenName(), password, password, "", correctEmail);
        Assert.assertEquals("User name is already taken", signUpPage.getErrorText());
    }

    @Test
    public void invalidEmailError(){
        signUpPage.signUp(correctName,password,password,"",incorrectEmail);
        Assert.assertEquals("Invalid e-mail address", signUpPage.getErrorText());
    }

    @Test
    public void passwordIsRequiredError(){
        signUpPage.signUp(correctName,null,password,"",correctEmail);
        Assert.assertEquals("Password is required", signUpPage.getErrorText());
    }

    @Test
    public void usernameIsRequiredError(){
        signUpPage.signUp(null, password, password, "", correctEmail);
        Assert.assertEquals("User name is required", signUpPage.getErrorText());
    }

    @Test
    public void passwordDidntMatchError(){
        signUpPage.signUp(correctName, password, incorrectConfirmPassword, "", correctEmail);
        Assert.assertEquals("Password didnt match", signUpPage.getErrorText());
    }

    @Test
    public void successSignUp(){
        signUpPage.signUp(correctName, password, password, "", correctEmail);
        Assert.assertEquals("Success", driver.findElement(By.cssSelector("#main-panel-content h1")).getText());
    }


    public String takenName(){
        SignUpPage signUpPage = new SignUpPage(driver).get();
        signUpPage.signUp(correctName,password,password,"",correctEmail);
        signUpPage.get();
        return correctName;
    }

    private static WebDriver getDriver() {
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


    /*@Test
    public void test() {
        String login = "admin";
        String password = "admin";

        LoginPage page = new LoginPage(driver).get();
        HomePage homePage = page.signIn(login, password);
        page = homePage.logout();
        LoginErrorPage loginErrorPage = page.incorrectSignIn(login, password + "1");
        page = loginErrorPage.tryAgain();
        page.signUp();
    }*/


}
