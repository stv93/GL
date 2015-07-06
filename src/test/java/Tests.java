import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import other.RandomForPages;
import pages.SignUpPage;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
@RunWith(Parameterized.class)
public class Tests{
    public static WebDriver driver;
    public static SignUpPage signUpPage;
    private String authorize;
    String correctName = RandomForPages.randomString(20);
    String correctEmail = RandomForPages.randomString(5)+"@";
    String incorrectEmail = RandomForPages.randomString(5);
    String password = RandomForPages.randomString(6);
    String incorrectConfirmPassword = RandomForPages.randomString(6);

    public Tests(String authorize) {
        this.authorize = authorize;
    }


    @Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{{"authorized"}, {"notauthorized"}});
    }

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
        authorizeVerify();
        signUpPage = new SignUpPage(driver).get();
    }

    @After
    public void after(){
        if(authorize.equals("authorized")){
            driver.manage().deleteAllCookies();
        }
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
        signUpPage.signUp(correctName, null, password, "", correctEmail);
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
    public void successfulSignUp(){
        signUpPage.signUp(correctName, password, password, "", correctEmail);
        Assert.assertEquals("Success", driver.findElement(By.cssSelector("#main-panel-content h1")).getText());
        driver.manage().deleteAllCookies();
    }

    @Test
    public void unsuccessfulSignUp(){
        signUpPage.signUp(RandomForPages.incorrectRandomUsername(), password, password, "", correctEmail);
        Assert.assertEquals(" Oops!", driver.findElement(By.cssSelector("#main-panel-content h1")).getText());
    }


    public String takenName(){
        SignUpPage signUpPage = new SignUpPage(driver).get();
        signUpPage.signUp(correctName, password, password, "", correctEmail);
        driver.manage().deleteAllCookies();
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

    public void authorizeVerify(){
        switch (authorize) {
            case "authorized": {
                signUpPage = new SignUpPage(driver).get();
                signUpPage.signUp(RandomForPages.randomString(20), password, password, "", correctEmail);
            }
            break;
            case "notauthorized":
                break;
        }
    }


}
