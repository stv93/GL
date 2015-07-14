package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


/**
 * Created by tetiana.sviatska on 7/1/2015.
 */
public abstract class Page<T extends Page<T>> extends LoadableComponent<T> {

    protected Logger log = LogManager.getLogger(this);
    protected WebDriver driver;
    protected WebDriverWait wait;

    @FindBy(id = "search-box")
    private WebElement searchBox;

    @FindBy(css = ".yui-ac-content")
    private WebElement autoComplete;

    private String currentPageUrl;

    public Page(WebDriver driver, String pageUrl) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.currentPageUrl = pageUrl.replaceAll("/$", "");
        wait = new WebDriverWait(driver, 2, 300);
    }

    public String getUrl(){
        return currentPageUrl;
    }

    protected void clearField(WebElement field) {
        int length = Optional.ofNullable(field.getAttribute("value"))
            .map(String::length)
            .orElse(field.getText().length());
        for (int i = length; i > 0; i--) {
            field.sendKeys(Keys.BACK_SPACE);
        }
    }

    public UserPage autoCompleteSearch(String subString, String expectedResult) {
        log.info("Searching for {} with selecting from autocomlete list {}", subString, expectedResult);
        boolean presenceOfResult = false;
        clearField(searchBox);
        try {
            wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(autoComplete)));
        } catch (org.openqa.selenium.TimeoutException e) {
            log.debug(e);
        }
        searchBox.sendKeys(subString);
        try {
            wait.until(ExpectedConditions.visibilityOf(autoComplete));
        } catch (org.openqa.selenium.TimeoutException e) {
            throw new RuntimeException("There isn't such value: " + subString);
        }
        List<WebElement> list = autoComplete.findElements(By.tagName("li"));
        for (WebElement li : list) {
            if (li.getText().equals(expectedResult)) {
                presenceOfResult = true;
                li.click();
                searchBox.sendKeys(Keys.ENTER);
                break;
            }
        }
        if (!presenceOfResult) {
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
        String url = driver.getCurrentUrl().replaceAll("/$", "");
        Assert.assertThat("Not on the right page.", currentPageUrl, Matchers.equalToIgnoringCase(url));
    }
}
