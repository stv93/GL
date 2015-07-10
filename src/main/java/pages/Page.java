package pages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import java.util.List;
import java.util.concurrent.TimeoutException;


/**
 * Created by tetiana.sviatska on 7/1/2015.
 */
public abstract class Page<T extends Page<T>> extends LoadableComponent<T> {

    @FindBy(id = "search-box")
    private WebElement searchBox;

    @FindBy(css = ".yui-ac-content")
    private WebElement autoComplete;

    protected Logger log = LogManager.getLogger(this);
    protected WebDriver driver;
    private String currentPageUrl;
    protected WebDriverWait wait;


    public Page(WebDriver driver, String pageUrl) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.currentPageUrl = pageUrl;
        wait = new WebDriverWait(driver, 2, 300);
    }

    public UserPage autoCompleteSearch(String subString, String expectedResult){
        log.info("Searching for {} with selecting from autocomlete list {}", subString, expectedResult);
        boolean presenceOfResult = false;
        Actions action = new Actions(driver);
        searchBox.sendKeys(subString);
        try {
            wait.until(ExpectedConditions.visibilityOf(autoComplete));
        }catch(org.openqa.selenium.TimeoutException e){
            throw new RuntimeException("There isn't such value: " + subString);
        }
        List<WebElement> list = autoComplete.findElements(By.tagName("li"));
        for (WebElement li : list) {
            if (li.getText().equals(expectedResult)) {
                presenceOfResult = true;
                action.click(li).sendKeys(Keys.ENTER).perform();
                break;
            }
        }
        if(!presenceOfResult){
            throw new RuntimeException("Item: " + expectedResult + " is absent in the list");
        }
        return new UserPage(driver, expectedResult);
    }

    @Override
    protected void load() {
        log.debug("Loading url: {}", currentPageUrl);
        driver.get(currentPageUrl);
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        Assert.assertTrue("Not on the right page.", url.equals(currentPageUrl));
    }

}
