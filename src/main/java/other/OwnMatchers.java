package other;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

/**
 * Created by tetiana.sviatska on 7/22/2015.
 */
public class OwnMatchers {

    public static Matcher<By> presenceOfElementLocatedBy(WebDriver driver){
        return new BaseMatcher<By>() {
            @Override
            public boolean matches(Object object) {
                By locator = (By) object;
                try {
                    WebElement element = driver.findElement(locator);
                    element.isDisplayed();
                    return true;
                } catch (WebDriverException e) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Element is absent or not displayed");
            }
        };
    }


    public static Matcher<WebElement> presenceOfElement(){
        return new BaseMatcher<WebElement>() {
            @Override
            public boolean matches(Object object) {
                WebElement element = (WebElement) object;
                try {
                    element.isDisplayed();
                    return true;
                } catch (WebDriverException e) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Element is absent or not displayed");
            }
        };
    }

    public static Matcher<WebElement[]> presenceAnyOfElements(){
        return new BaseMatcher<WebElement[]>() {
            @Override
            public boolean matches(Object object) {
                WebElement[] array = (WebElement[]) object;
                return Arrays.asList(array).stream().anyMatch(WebElement::isDisplayed);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Element is absent or not displayed");
            }
        };
    }

}
