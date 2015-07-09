import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.runners.model.Statement;
import org.openqa.selenium.WebDriver;
import other.MethodsForTests;
import pages.HomePage;
import pages.Page;


/**
 * Created by tetiana.sviatska on 7/8/2015.
 */
public class SearchTests {
    public static WebDriver driver;
    public HomePage page;

    @ClassRule
    public static TestRule setUpDriverAndCleanUpUsersRule = (base, d) -> new Statement() {
        @Override
        public void evaluate() throws Throwable {
            driver = MethodsForTests.getDriver();
            MethodsForTests.logInAsAdmin(driver);
            try {
                base.evaluate();
            }
            finally {
               driver.quit();
            }
        }
    };

    @Before
    public void before(){
        page = new HomePage(driver).get();
    }

    @Test
    public void test(){
        page.search("t", "test");
    }

}
