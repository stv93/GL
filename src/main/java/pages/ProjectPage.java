package pages;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import other.MethodsForTests;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by tetiana.sviatska on 7/13/2015.
 */
public class ProjectPage extends AuthenticationBasePage<ProjectPage> {

    @FindBy(css = ".build-row [class*=\"zws-inserted\"]")
    private List<WebElement> builds;

    @FindBy(css = ".task-link[href=\"#\"]")
    private WebElement deleteProjectButton;

    private String projectName;

    public ProjectPage(@NotNull WebDriver driver, @NotNull String projectName) {
        super(driver);
        this.projectName = projectName;
    }

    protected ProjectPage(@NotNull WebDriver driver, boolean checkIfLoaded) {
        super(driver, checkIfLoaded);
    }

    @Override
    protected void verifyUniqueElement() throws Error {
        Assert.assertTrue(deleteProjectButton.isDisplayed());
    }

    @Override
    public String getPageUrl() {
        return String.format("http://seltr-kbp1-1.synapse.com:8080/job/%s/", MethodsForTests.encode(projectName));
    }

    /*protected static ProjectPage createProjectPageWithLoadingValidation(@NotNull WebDriver driver, @NotNull String projectName) {
        return new ProjectPage(driver, true) {
            @Override
            public String getPageUrl() {
                return String.format("http://seltr-kbp1-1.synapse.com:8080/job/%s/", MethodsForTests.encode(projectName));
            }
        };
    }*/
}
