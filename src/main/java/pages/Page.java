package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import other.MethodsForTests;

import java.util.List;
import java.util.Optional;

/**
 * Created by tetiana.sviatska on 7/1/2015.
 */
public abstract class Page<T extends Page<T>> extends LoadableComponent<T> {

    public static final int SHORT_TIMEOUT = 5;
    public static final int DEFAULT_TIMEOUT = 30;


    protected Logger log = LogManager.getLogger(this);
    protected WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "search-box")
    private WebElement searchBox;

    @FindBy(css = ".yui-ac-content")
    private WebElement autoComplete;

    @FindBy(css = ".login a[href*=\"/logout\"]")
    private WebElement logOut;

    @FindBy(id = "jenkins-head-icon")
    private WebElement icon;

    public abstract String getPageUrl();

    public Page(WebDriver driver) {
        this(driver, false);
    }

    protected Page(WebDriver driver, boolean checkIfLoaded) {
        this.driver = driver;
        wait = new WebDriverWait(driver, DEFAULT_TIMEOUT, 300);
        PageFactory.initElements(driver, this);
        if (checkIfLoaded) {
            waitForDocumentCompleteState();
            isLoaded();
        }
    }

    protected void clearField(WebElement field) {
        int length = Optional.ofNullable(field.getAttribute("value"))
                .map(String::length)
                .orElse(field.getText().length());
        field.sendKeys(Keys.END);
        for (int i = length; i > 0; i--) {
            field.sendKeys(Keys.BACK_SPACE);
        }
    }


    protected void waitForDocumentCompleteState() {
        try {
            wait.until((ExpectedCondition<Boolean>) wd -> "complete".equals(
                    ((JavascriptExecutor) wd).executeScript("return document.readyState").toString()));
        }
        catch (TimeoutException e) {}
    }

    protected boolean isLoggedIn() {
        try {
            return logOut.isDisplayed();
        } catch (NoSuchElementException e) {
        }
        return false;
    }

    private List<WebElement> search(String token) throws RuntimeException {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(searchBox)
                .click()
                .sendKeys(searchBox, token)
                .perform();
        return new WebDriverWait(driver, SHORT_TIMEOUT)
                .until(ExpectedConditions.visibilityOf(autoComplete))
                .findElements(By.cssSelector("li"));
        }
        catch(TimeoutException e){
            throw new RuntimeException("There is no such value: " + token);
        }
    }

    public UserPage autoCompleteSearch(String token, String searched) throws RuntimeException {
        log.info("Searching for {} with selecting from autocomplete list {}", token, searched);
        clearField(searchBox);
        new WebDriverWait(driver, SHORT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".yui-ac-content")));
        try {
            new Actions(driver).moveToElement(
                    search(token).stream()
                            .filter(x -> x.getText().equals(searched))
                            .findFirst()
                            .get())
                    .click()
                    .sendKeys(Keys.ENTER)
                    .perform();
            new WebDriverWait(driver, SHORT_TIMEOUT).until(ExpectedConditions.urlContains(MethodsForTests.encode(searched.toLowerCase())));
            return UserPage.createUserPageWithLoadingValidation(driver, searched);
        }
        catch (java.util.NoSuchElementException e){
            throw new RuntimeException("Item: " + searched + " is absent in the list");
        }
    }

    @Override
    protected void load() {
        log.debug("Loading url: {}", getPageUrl());
        driver.get(getPageUrl());
        waitForDocumentCompleteState();
    }

    //protected abstract void verifyUniqueElement() throws Error;

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl().replaceAll("/$", "");
        Assert.assertThat("Not on the right page.", getPageUrl().replaceAll("/$", ""), Matchers.equalToIgnoringCase(url));
        // verifyUniqueElement();
    }
}
