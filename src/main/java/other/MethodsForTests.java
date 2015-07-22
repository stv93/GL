package other;

import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import pages.LoginPage;
import pages.SignUpPage;
import pages.UserDeletingPage;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by tetiana.sviatska on 7/8/2015.
 */
public class MethodsForTests {

    private MethodsForTests(){}

    public static final String DEFAUL_PASSWORD = "defaulPassword";

    public static WebDriver getDriver() {
        WebDriver driver;
        String browser = System.getProperty("browser");
        String language = System.getProperty("language");
        switch (browser.toLowerCase()) {
            case "firefox":
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("intl.accept_languages", language);
                profile.setEnableNativeEvents(true);
                driver = new FirefoxDriver(profile);
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "ChromeDriver\\chromedriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--lang=" + language);
                driver = new ChromeDriver(options);
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
     * This method is used to create new user
     *
     * @return login of the user which was used for authentication or {@code null} if there was an exception
     */
    @Nullable
    public static String createUser(@Nullable Collection<String> list) {
        WebDriver wd = null;
        try {
            wd = new FirefoxDriver();
            SignUpPage signUpPage = new SignUpPage(wd).get();
            String randomName = RandomForPages.randomString(20);
            String correctEmail = RandomForPages.randomString(6) + "@";
            signUpPage.signUp(randomName, DEFAUL_PASSWORD, DEFAUL_PASSWORD, randomName, correctEmail);
            Optional.ofNullable(list).ifPresent(l -> l.add(randomName));
            return randomName;
        } catch (Exception e) {
            LogManager.getLogger().debug(e.getMessage());
        } finally {
            if (wd != null) {
                wd.quit();
            }
        }
        return null;
    }

    public static void usersClearing(@NotNull Iterable<String> list, @NotNull WebDriver driver) {
        LogManager.getLogger().debug("Removing users");
        for (String name : list) {
            try {
                UserDeletingPage userDeletingPage = new UserDeletingPage(driver, name).get();
                userDeletingPage.deleteUser();
            } catch (Exception e) {
                LogManager.getLogger().debug(e.getMessage());
            }
        }
        LogManager.getLogger().debug("Users is deleted");
    }

    public static void makeScreenshot(@NotNull String name, @NotNull WebDriver driver) {
        LogManager.getLogger().debug("Taking screenshot");
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File output = generateScreenshotName(name);
        output.mkdirs();
        try {
            copy(scrFile, output);
        }
        catch (InvalidPathException e) {
            LogManager.getLogger().error("Can't create screenshot using specified name, falling back to default");
            copy(scrFile, generateScreenshotName("screenshot"));
        }
    }

    private static void copy(File source, File target) {
        try {
            Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            LogManager.getLogger().error(ioe);
        }
    }

    private static File generateScreenshotName(String name) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH.mm.ss");
        return new File("Screenshots", String.format("%s %s.png", name, formatter.format(LocalDateTime.now())));
    }

    public static void logInAsAdmin(@NotNull WebDriver driver) {
        LogManager.getLogger().info("Logging in as admin");
        try {
            LoginPage log = new LoginPage(driver).get();
            log.signIn("admin", "admin");
        } catch (Exception e) {
            LogManager.getLogger().debug(e.getMessage());
        }
    }

    @NotNull
    public static String encode(@NotNull String str) {
        try {
            return URLEncoder.encode(str, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
}
