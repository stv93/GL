import com.thoughtworks.selenium.ScreenshotListener;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.model.Statement;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import other.RandomForPages;
import pages.LoginPage;
import pages.SignUpPage;
import pages.UserDeletingPage;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
@RunWith(Parameterized.class)
public class Tests {

    public static Logger logger = LogManager.getLogger(Tests.class);
    public static WebDriver driver;
    public static SignUpPage signUpPage;
    private String authorize;
    private static String browser;

    private static ArrayList<String> list = new ArrayList<>();

    String correctName = RandomForPages.randomString(20);
    String correctEmail = RandomForPages.randomString(5) + "@";
    String incorrectEmail = RandomForPages.randomString(5);
    String password = RandomForPages.randomString(6);
    String incorrectConfirmPassword = RandomForPages.randomString(6);

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface NeedsCleanUp {}

    @Rule
    public TestWatcher watcher = new TestWatcher() {

        @Override
        protected void starting(Description d) {
            logger.info("Starting test: {}", d.getMethodName());
        }

        @Override
        protected void skipped(AssumptionViolatedException e, Description d) {
            logger.info("Test: {} - SKIPPED", d.getMethodName());
        }

        @Override
        protected void failed(Throwable e, Description d) {
            logger.info("Test: {} - FAILED. Reason: {}", d.getMethodName(), e.getMessage());
            makeScreenshot(d);
        }

        @Override
        protected void succeeded(Description d) {
            logger.info("Test: {} - PASSED", d.getMethodName());
        }

    };

    @Rule
    public TestRule cleaningCookiesRule = (Statement base, Description d) -> new Statement() {
        @Override
        public void evaluate() throws Throwable {
            try {
                base.evaluate();
            }
            finally {
                if (d.getAnnotation(NeedsCleanUp.class) != null) {
                    logger.debug("Clearing cookies");
                    driver.manage().deleteAllCookies();
                }
            }
        }
    };


    public Tests(String authorize) {
        this.authorize = authorize;
    }


    @Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{"authorized"}/*,{"notauthorized"}*/});
    }

    @BeforeClass
    public static void beforeForAll() {
        driver = getDriver();
        logger.info("Running tests in browser " + browser);
    }

    @AfterClass
    public static void afterForAll() {
        usersClearing(list);
        driver.quit();
    }

    @Before
    public void before() {
        authorizeVerify();
        signUpPage = new SignUpPage(driver).get();
    }

    @After
    public void after() {
        if (authorize.equals("authorized")) {
            list.add(correctName);
            driver.manage().deleteAllCookies();
        }
    }

    @Test
    public void nameIsTakenError() {
        String username = authorizedUser();
        Assume.assumeThat(username, CoreMatchers.notNullValue());
        signUpPage.signUp(username, password, password, "", correctEmail);
        Assert.assertEquals("User name is already taken", signUpPage.getErrorText());
    }

    @Test
    public void invalidEmailError() {
        signUpPage.signUp(correctName, password, password, "", incorrectEmail);
        Assert.assertEquals("Invalid e-mail address", signUpPage.getErrorText());
    }

    @Test
    public void passwordIsRequiredError() {
        signUpPage.signUp(correctName, null, password, "", correctEmail);
        Assert.assertEquals("Password is required", signUpPage.getErrorText());
    }

    @Test
    public void usernameIsRequiredError() {
        signUpPage.signUp(null, password, password, "", correctEmail);
        Assert.assertEquals("User name is required", signUpPage.getErrorText());
    }

    @Test
    public void passwordDidntMatchError() {
        signUpPage.signUp(correctName, password, incorrectConfirmPassword, "", correctEmail);
        Assert.assertEquals("Password didnt match", signUpPage.getErrorText());
    }

    @NeedsCleanUp
    @Test
    public void successfulSignUp() {
        signUpPage.signUp(correctName, password, password, "", correctEmail);
        Assert.assertEquals("Success", driver.findElement(By.cssSelector("#main-panel-content h1")).getText());
    }

    @Test
    public void unsuccessfulSignUp() {
        String incorrectUserName = RandomForPages.randomStringWithInvalidSymbols();
        signUpPage.signUp(incorrectUserName, password, password, "", correctEmail);
        Assert.assertEquals(" Oops!", driver.findElement(By.cssSelector("#main-panel-content h1")).getText());
    }
    public static String authorizedUser(){
        try {
            signUpPage = new SignUpPage(driver).get();
            String randomName = RandomForPages.randomString(20);
            String password = RandomForPages.randomString(5);
            String correctEmail = RandomForPages.randomString(6) + "@";
            signUpPage.signUp(randomName, password, password, RandomForPages.randomString(5), correctEmail);
            driver.manage().deleteAllCookies();
            logger.debug("Clearing cookies");
            signUpPage.get();
            return randomName;
        }
        catch (Exception e) {
            logger.debug(e.getMessage());
        }
        return null;
    }

    public void authorizeVerify() {
        switch (authorize) {
            case "authorized": {
                String name = authorizedUser();
                list.add(name);
            }
            break;
            case "notauthorized":
                break;
        }
    }

    private static WebDriver getDriver() {
        browser = System.getProperty("browser");
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

    public static void usersClearing(ArrayList<String> list){
        LoginPage log = new LoginPage(driver).get();
        log.signIn("admin", "admin");
        for (String name : list){
            try{
                UserDeletingPage userDeletingPage = new UserDeletingPage(driver, name).get();
                userDeletingPage.deleteUser();
            }catch (Exception e){
                logger.debug(e.getMessage());
            }
        }
        logger.debug("Users is deleted");
    }

    public static void makeScreenshot(Description d) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(
                OutputType.FILE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
        String date =  sdf.format(Calendar.getInstance().getTime());
        String scrFilename = d.getMethodName()+ date +"-Screenshot.jpeg";
        File outputFile = new File("D:\\Screenshots", scrFilename);
        try {
            FileUtils.copyFile(scrFile, outputFile);
        } catch (IOException ioe) {
            logger.error("Erroring screenshot after exception.", ioe);
        }
    }
}
