package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.Optional;

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

    @FindBy(css = ".login a[href*=\"/logout\"]")
    private WebElement logOut;

    @FindBy(id = "jenkins-head-icon")
    private WebElement icon;

    public abstract String getPageUrl();

    public Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 2, 300);
        PageFactory.initElements(driver, this);
    }

    protected void clearField(WebElement field) {
        int length = Optional.ofNullable(field.getAttribute("value"))
                .map(String::length)
                .orElse(field.getText().length());
        for (int i = length; i > 0; i--) {
            field.sendKeys(Keys.BACK_SPACE);
        }
    }

    protected boolean isLoggedIn() {
        try {
            return logOut.isDisplayed();
        } catch (NoSuchElementException e) {
        }
        return false;
    }

    public UserPage autoCompleteSearch(String subString, String expectedResult) {
        log.info("Searching for {} with selecting from autocomlete list {}", subString, expectedResult);
        boolean presenceOfResult = false;
        clearField(searchBox);
        try {
            wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(autoComplete)));
        } catch (TimeoutException e) {
            log.debug(e);
        }
        searchBox.sendKeys(subString);
        try {
            wait.until(ExpectedConditions.visibilityOf(autoComplete));
        } catch (TimeoutException e) {
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
        UserPage userPage = new UserPage(driver, expectedResult);
        if (userPage.isOnThePage()){
            return new UserPage(driver, expectedResult);
        }
        return null;
    }

    private boolean isInTheJenkins(){
        try {
            return icon.isDisplayed();
        } catch (NoSuchElementException e) {
        }
        return false;
    }

    protected boolean isOnThePage(){
        boolean check = false;
        for (int i = 0; i < 10; i++) {
                try{
                    isLoaded();
                    check = true;
                    break;
                }
                catch(Error e){
                }
            }
        return check;
    }

    @Override
    protected void load() {
        log.debug("Loading url: {}", getPageUrl());
        driver.get(getPageUrl());
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl().replaceAll("/$", "");
//        if(!isInTheJenkins()){
//            throw new Error("There is no such page:" + getPageUrl());
//        }
        Assert.assertThat("Not on the right page.", getPageUrl().replaceAll("/$", ""), Matchers.equalToIgnoringCase(url));
    }
}
