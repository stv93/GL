package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

/**
 * Created by tetiana.sviatska on 7/1/2015.
 */
public abstract class Page<T extends Page<T>> extends LoadableComponent<T> {

    protected Logger log = LogManager.getLogger(this);
    protected WebDriver driver;
    private String currentPageUrl;

    public Page(WebDriver driver, String pageUrl) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.currentPageUrl = pageUrl;
    }

    @Override
    protected void load() {
        log.debug("Loading url: {}", currentPageUrl);
        driver.get(currentPageUrl);
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        Assert.assertTrue("Not on the right page.", url.contains(currentPageUrl));
    }

}
