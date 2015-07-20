import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.model.Statement;
import other.MethodsForTests;
import other.RandomForPages;
import pages.SignUpPage;
import pages.SignUpResultPage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
@RunWith(Parameterized.class)
public class SignUpTests extends BaseTests {

    public static SignUpPage signUpPage;

    private boolean authorize;
    private static String authorizedUserName;

    private static Set<String> list = new HashSet<>();

    String correctName = RandomForPages.randomString(20);
    String correctEmail = RandomForPages.randomString(5) + "@";
    String incorrectEmail = RandomForPages.randomString(5);
    String password = RandomForPages.randomString(6);
    String incorrectConfirmPassword = RandomForPages.randomString(6);

    public SignUpTests(boolean authorized) {
        authorize = authorized;
    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{true}, {false}});
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface NeedsCleanUp {}

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

    @ClassRule
    public static TestRule setUpDriverAndCleanUpUsersRule = (base, d) -> new Statement() {
        @Override
        public void evaluate() throws Throwable {
            driver = MethodsForTests.getDriver();
            try {
                base.evaluate();
            }
            finally {
                MethodsForTests.logInAsAdmin(driver);
                MethodsForTests.usersClearing(list, driver);
                driver.quit();
            }
        }
    };

    @Rule
    public TestRule parametrizationHandlingRule = (base, d) -> new Statement() {
        @Override
        public void evaluate() throws Throwable {
            if (authorizedUserName == null) {
                authorizedUserName = MethodsForTests.makeAuthenticatedSession(list);
            }
            base.evaluate();
        }
    };


    @Before
    public void before() {
        signUpPage = new SignUpPage(driver).get();
    }

    /*@After
    public void after() {
        if (authorize) {
            driver.manage().deleteAllCookies();
        }
    }*/

    @Ignore
    @Test
    public void nameIsTakenError() {
        // FIXME I'm pre-condition
        String username = MethodsForTests.makeAuthenticatedSession(list);
        Assume.assumeThat(username, CoreMatchers.notNullValue());

        SignUpResultPage resultPage = signUpPage.signUp(username, password, password, "", correctEmail);
        Assert.assertEquals("User name is already taken", resultPage.getErrorText());
    }

    @Test
    public void invalidEmailError() {
        SignUpResultPage resultPage = signUpPage.signUp(correctName, password, password, "", incorrectEmail);
        Assert.assertEquals("Invalid e-mail address", resultPage.getErrorText());
    }

    @Test
    public void passwordIsRequiredError() {
        SignUpResultPage resultPage = signUpPage.signUp(correctName, null, password, "", correctEmail);
        Assert.assertEquals("Password is required", resultPage.getErrorText());
    }

    @Test
      public void usernameIsRequiredError() {
        SignUpResultPage resultPage = signUpPage.signUp(null, password, password, "", correctEmail);
        Assert.assertEquals("User name is required", resultPage.getErrorText());
    }

    @Test
    public void passwordDidntMatchError() {
        SignUpResultPage resultPage = signUpPage.signUp(correctName, password, incorrectConfirmPassword, "",
                correctEmail);
        Assert.assertEquals("Password didnt match", resultPage.getErrorText());
    }

    @Ignore
    @NeedsCleanUp
    @Test
    public void successfulSignUp() {
        SignUpResultPage resultPage = signUpPage.signUp(correctName, password, password, "", correctEmail);
        Assert.assertEquals("Success", resultPage.getMessageText());
    }

    @Ignore
    @Test
    public void unsuccessfulSignUp() {
        String incorrectUserName = RandomForPages.randomStringWithInvalidSymbols();
        SignUpResultPage resultPage = signUpPage.signUp(incorrectUserName, password, password, "", correctEmail);
        Assert.assertEquals(" Oops!", resultPage.getMessageText());
    }

}