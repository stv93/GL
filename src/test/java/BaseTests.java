import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AssumptionViolatedException;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;
import other.MethodsForTests;

/**
 * Created by tetiana.sviatska on 7/13/2015.
 */
public class BaseTests {

    public static WebDriver driver;
    protected final Logger logger = LogManager.getLogger(Tests.class);

    @Rule
    public TestWatcher watcher = new TestWatcher() {

        @Override
        protected void starting(Description d) {
            logger.info("Starting test: {}", d.getMethodName());
        }

        @Override
        protected void skipped(AssumptionViolatedException e, Description d) {
            logger.info("Test: {} - SKIPPED. Reason: {}", d.getMethodName(), e.getMessage());
        }

        @Override
        protected void failed(Throwable e, Description d) {
            logger.info("Test: {} - FAILED. Reason: {}", d.getMethodName(), e.getMessage());
            MethodsForTests.makeScreenshot(d.getMethodName(), driver);
        }

        @Override
        protected void succeeded(Description d) {
            logger.info("Test: {} - PASSED", d.getMethodName());
        }

    };
}
