import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.runners.model.Statement;
import org.openqa.selenium.WebDriver;
import other.MethodsForTests;
import other.RandomForPages;
import pages.HomePage;
import pages.Page;
import pages.UserPage;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by tetiana.sviatska on 7/8/2015.
 */
public class SearchTests {
    private static WebDriver driver;
    private HomePage page;
    private static String expectedResult;
    private static Set<String> list = new HashSet<>();

    @ClassRule
    public static TestRule setUpDriverAndCleanUpUsersRule = (base, d) -> new Statement() {
        @Override
        public void evaluate() throws Throwable {
            driver = MethodsForTests.getDriver();
            expectedResult = MethodsForTests.makeAuthenticatedSession(driver,list);
            MethodsForTests.logInAsAdmin(driver);
            try {
                base.evaluate();
            }
            finally {
                MethodsForTests.usersClearing(list, driver);
               //driver.quit();
            }
        }
    };


    @Before
    public void before(){
        page = new HomePage(driver).get();
    }


    @Test
    public void selectExistentValueFromAutoComplete(){
        UserPage userPage = page.autoCompleteSearch(expectedResult.substring(10), expectedResult);
        Assert.assertEquals(expectedResult, userPage.getName());
    }

    @Test
    public void selectNonExistentValueFromAutoComplete(){
        page.autoCompleteSearch(expectedResult, expectedResult.substring(10));
    }
}
