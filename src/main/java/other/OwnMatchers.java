package other;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;

/**
 * Created by tetiana.sviatska on 7/22/2015.
 */
public class OwnMatchers {

    public static Matcher<By> presenceOfElementLocatedBy(@NotNull WebDriver driver){
        return new TypeSafeMatcher<By>() {
            @Override
            protected boolean matchesSafely(By by) {
                try {
                    WebElement element = driver.findElement(by);
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
        return new TypeSafeMatcher<WebElement>() {
            @Override
            protected boolean matchesSafely(WebElement element) {
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
        return new TypeSafeMatcher<WebElement[]>(){

            @Override
            public void describeTo(Description description) {
                description.appendText("Element is absent or not displayed");
            }

            @Override
            protected boolean matchesSafely(WebElement[] webElements) {
                for (WebElement element: webElements){
                    try {
                        element.isDisplayed();
                        return true;
                    }
                    catch (NoSuchElementException e) {}
                }
                return false;
            }
            };
        }

}
