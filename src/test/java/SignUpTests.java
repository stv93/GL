import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.model.Statement;
import other.*;
import pages.LoginPage;
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
        return Arrays.asList(new Object[][]{/*{true}, */{false}});
    }

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
            if (authorize && authorizedUserName == null) {
                authorizedUserName = MethodsForTests.createUser(list);
                new LoginPage(driver).get().signIn(authorizedUserName, MethodsForTests.DEFAULT_PASSWORD);
            }
            if(!authorize){
                driver.manage().deleteAllCookies();
            }
            signUpPage = new SignUpPage(driver).get();
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
        Assume.assumeThat(userName, CoreMatchers.notNullValue());

        SignUpResultPage resultPage = signUpPage.signUp(userName, password, password, "", correctEmail);
        System.out.println(resultPage.getErrorText());
        Assert.assertEquals(Source.getValue("SignUpErrorNameIsTakenError"), resultPage.getErrorText());
    }

    @Test
    public void invalidEmailError() {
        SignUpResultPage resultPage = signUpPage.signUp(correctName, password, password, "", incorrectEmail);
        System.out.println(resultPage.getErrorText());
        Assert.assertEquals(Source.getValue("SignUpErrorInvalidEmailError"), resultPage.getErrorText());
    }

    @Test
    public void passwordIsRequiredError() {
        SignUpResultPage resultPage = signUpPage.signUp(correctName, null, password, "", correctEmail);
        System.out.println(resultPage.getErrorText());
        Assert.assertEquals(Source.getValue("SignUpErrorPasswordIsRequired"), resultPage.getErrorText());
    }

    @Test
      public void usernameIsRequiredError() {
        SignUpResultPage resultPage = signUpPage.signUp(null, password, password, "", correctEmail);
        System.out.println(resultPage.getErrorText());
        Assert.assertEquals(Source.getValue("SignUpErrorUsernameIsRequiredError"), resultPage.getErrorText());
    }

    @Test
    public void passwordDidntMatchError() {
        SignUpResultPage resultPage = signUpPage.signUp(correctName, password, incorrectConfirmPassword, "",
                correctEmail);
        System.out.println(resultPage.getErrorText());
        Assert.assertEquals(Source.getValue("SignUpErrorPasswordDidntMatchError"), resultPage.getErrorText());
    }

    @Test
    public void successfulSignUp() {
        SignUpResultPage resultPage = signUpPage.signUp(correctName, password, password, "", correctEmail);
        System.out.println(resultPage.getMessageText());
        Assert.assertEquals(Source.getValue("SignUpSuccess"), resultPage.getMessageText());
    }

    @Test
    public void unsuccessfulSignUp() {
        String incorrectUserName = RandomForPages.randomStringWithInvalidSymbols();
        thrown.expectMessage("Not on the right page");
        SignUpResultPage resultPage = signUpPage.signUp(incorrectUserName, password, password, "", correctEmail);
        System.out.println(resultPage.getMessageText());
        Assert.assertEquals(" Oops!", resultPage.getMessageText());
    }
}
