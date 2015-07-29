package pages;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import other.MethodsForTests;
import other.OwnMatchers;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by tetiana.sviatska on 7/13/2015.
 */
public class ProjectPage extends AuthenticationBasePage<ProjectPage> {

    @FindBy(css = ".build-row [class*=\"zws-inserted\"]")
    private List<WebElement> builds;

    @FindBy(css = ".task-link[href=\"#\"]")
    private WebElement deleteProjectButton;

    @FindBy(css = "a.task-link[href*=\"build?delay=0sec\"]")
    private WebElement createBuild;

    private String projectName;

    public ProjectPage(@NotNull WebDriver driver, @NotNull String projectName) {
        super(driver);
        this.projectName = projectName;
    }

    protected ProjectPage(@NotNull WebDriver driver, boolean checkIfLoaded) {
        super(driver, checkIfLoaded);
    }

    public int getLastBuildNumber(){
        try {
            return builds.stream().mapToInt(element -> Integer.parseInt(element.getText().replaceAll("#", ""))).max().getAsInt();
        }
        catch(NoSuchElementException e) {
            return 0;
        }
    }

    public ProjectPage createBuild() throws RuntimeException{
        log.info("Creating build");
        int lastBuild = getLastBuildNumber();
        createBuild.click();
        if (!(getLastBuildNumber()>lastBuild)) {
            try {
                wait.until(isBuildAdded());
            } catch (TimeoutException e) {
                throw new RuntimeException("Build is not created");
            }
        }
        return this;
    }

    @Override
    public String getPageUrl() {
        return String.format("http://seltr-kbp1-1.synapse.com:8080/job/%s/", MethodsForTests.encode(projectName));
    }

    @Override
    protected void verifyUniqueElement() throws Error {
        Assert.assertThat(deleteProjectButton, OwnMatchers.presenceOfElement());
    }

    protected static ProjectPage createProjectPageWithLoadingValidation(@NotNull WebDriver driver, @NotNull String projectName) {
        return new ProjectPage(driver, true) {
            @Override
            public String getPageUrl() {
                return String.format("http://seltr-kbp1-1.synapse.com:8080/job/%s/", MethodsForTests.encode(projectName));
            }
        };
    }

    private ExpectedCondition<Boolean> isBuildAdded(){
        return new ExpectedCondition<Boolean>(){

            int lastBuild = getLastBuildNumber();

            @Override
            public Boolean apply(@NotNull WebDriver input) {
                return (getLastBuildNumber() > lastBuild);
            }
        };
    }
}
