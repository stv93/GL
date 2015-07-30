import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.model.Statement;
import other.MethodsForTests;
import other.RandomForPages;
import other.Source;
import pages.LoginPage;
import pages.SecurityConfigurationPage;
import pages.SignUpPage;
import pages.SignUpResultPage;

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
    private static boolean allowedConfiguration;

    String correctName = RandomForPages.randomString(20);
    String correctEmail = RandomForPages.randomString(5) + "@";
    String incorrectEmail = RandomForPages.randomString(5);
    String password = RandomForPages.randomString(6);
    String incorrectConfirmPassword = RandomForPages.randomString(6);

    public SignUpTests(String description, boolean authorized) {
        authorize = authorized;
    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{"Authorizied", true}, {"Unauthorized", false}});
    }

    @ClassRule
    public static TestRule setUpDriverAndCleanUpUsersRule = (base, d) -> new Statement() {
        @Override
        public void evaluate() throws Throwable {
            try {
                try {
                    driver = MethodsForTests.getDriver();
                    allowedConfiguration = new SignUpPage(driver).get().isSignUpAllowed();
                    if (!allowedConfiguration) {
                        new SecurityConfigurationPage(driver).get().allowsSignUp();
                    }
                } catch (Throwable e) {
                    Assume.assumeNoException(e);
                }
                base.evaluate();
            }
            finally {
                if(!allowedConfiguration){
                    new SecurityConfigurationPage(driver).get().forbidsSignUp();
                }
                MethodsForTests.usersClearing(list, driver);
                driver.quit();
            }
        }
    };

    @Rule
    public TestRule parametrizationHandlingRule = (base, d) -> new Statement() {
        @Override
        public void evaluate() throws Throwable {
            Assume.assumeTrue(new SignUpPage(driver).get().isSignUpAllowed());
            try {

                if (authorize && authorizedUserName == null) {
                    authorizedUserName = MethodsForTests.createUser(list);
                    new LoginPage(driver).get().signIn(authorizedUserName, MethodsForTests.DEFAULT_PASSWORD);
                }
                if(!authorize){
                driver.manage().deleteAllCookies();
                }
                signUpPage = new SignUpPage(driver).get();
            }catch(Throwable e){
                Assume.assumeNoException(e);
            }
            base.evaluate();
        }
    };

    private String getExistentName(){
        if (!authorize)
            return MethodsForTests.createUser(list);
        return authorizedUserName;
    }

    @Test
    public void nameIsTakenError() {
        String userName = getExistentName();
        Assume.assumeNotNull(userName);

        SignUpResultPage resultPage = signUpPage.signUp(userName, password, password, "", correctEmail);
        Assert.assertEquals(Source.getValue("SignUpErrorNameIsTakenError"), resultPage.getErrorText());
    }

    @Test
    public void invalidEmailError() {
        SignUpResultPage resultPage = signUpPage.signUp(correctName, password, password, "", incorrectEmail);
        Assert.assertEquals(Source.getValue("SignUpErrorInvalidEmailError"), resultPage.getErrorText());
    }

    @Test
    public void passwordIsRequiredError() {
        SignUpResultPage resultPage = signUpPage.signUp(correctName, "", password, "", correctEmail);
        Assert.assertEquals(Source.getValue("SignUpErrorPasswordIsRequired"), resultPage.getErrorText());
    }

    @Test
      public void usernameIsRequiredError() {
        SignUpResultPage resultPage = signUpPage.signUp("", password, password, "", correctEmail);
        Assert.assertEquals(Source.getValue("SignUpErrorUsernameIsRequiredError"), resultPage.getErrorText());
    }

    @Test
    public void passwordDidntMatchError() {
        SignUpResultPage resultPage = signUpPage.signUp(correctName, password, incorrectConfirmPassword, "",
                correctEmail);
        Assert.assertEquals(Source.getValue("SignUpErrorPasswordDidntMatchError"), resultPage.getErrorText());
    }

    @Test
    public void successfulSignUp() {
        SignUpResultPage resultPage = signUpPage.signUp(correctName, password, password, "", correctEmail);
        Assert.assertEquals(Source.getValue("SignUpSuccess"), resultPage.getMessageText());
    }

    @Test
    public void unsuccessfulSignUp() {
        thrown.expectMessage("Not on the right page");
        SignUpResultPage resultPage = signUpPage.signUp(RandomForPages.randomStringWithInvalidSymbols(), password, password, "", correctEmail);
        Assert.assertEquals(" Oops!", resultPage.getMessageText());
    }
}
