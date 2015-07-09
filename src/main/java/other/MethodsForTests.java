package other;

import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import pages.HomePage;
import pages.LoginPage;
import pages.SignUpPage;
import pages.UserDeletingPage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by tetiana.sviatska on 7/8/2015.
 */
public abstract class MethodsForTests {

    public static WebDriver getDriver() {
        WebDriver driver;
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
        LogManager.getLogger().info("Running tests in browser " + browser);
        return driver;
    }

    /**
     * This method is used to sign up with random account just to become authorized
     * @param wd driver which should be used to perform authentication operation
     * @return login of the user which was used for authentication or {@code null} if there was an exception
     */
    @Nullable
    public static String makeAuthenticatedSession(@NotNull WebDriver wd){
        try {
            SignUpPage signUpPage = new SignUpPage(wd).get();
            String randomName = RandomForPages.randomString(20);
            String password = RandomForPages.randomString(5);
            String correctEmail = RandomForPages.randomString(6) + "@";
            signUpPage.signUp(randomName, password, password, RandomForPages.randomString(5), correctEmail);
            signUpPage.get();
            return randomName;
        }
        catch (Exception e) {
            LogManager.getLogger().debug(e.getMessage());
        }
        return null;
    }

    public static void usersClearing(Iterable<String> list, WebDriver driver){
        LogManager.getLogger().debug("Removing users");
        logInAsAdmin(driver);
        for (String name : list){
            try{
                UserDeletingPage userDeletingPage = new UserDeletingPage(driver, name).get();
                userDeletingPage.deleteUser();
            }catch (Exception e){
                LogManager.getLogger().debug(e.getMessage());
            }
        }
        LogManager.getLogger().debug("Users is deleted");
    }

    public static void makeScreenshot(String name, WebDriver driver) {
        LogManager.getLogger().debug("Taking screenshot");
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(
                OutputType.FILE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH.mm.ss");
        String scrFilename = name + formatter.format(LocalDateTime.now()) +"-Screenshot.png";
        File output = new File("Screenshots", scrFilename);
        output.mkdirs();
        try {
            Files.copy(scrFile.toPath(), output.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            LogManager.getLogger().error("Erroring screenshot after exception.", ioe);
        }
    }

    public static HomePage logInAsAdmin(WebDriver driver){
        LogManager.getLogger().info("Logging in as admin");
        try {
            LoginPage log = new LoginPage(driver).get();
            HomePage homePage = log.signIn("admin", "admin");
            return homePage;
        }
        catch(Exception e){
            LogManager.getLogger().debug(e.getMessage());
        }
        return null;
    }
}
