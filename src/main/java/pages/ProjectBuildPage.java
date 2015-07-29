package pages;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import other.MethodsForTests;
import other.OwnMatchers;

/**
 * Created by tetiana.sviatska on 7/13/2015.
 */
public class ProjectBuildPage extends AuthenticationBasePage<ProjectBuildPage> {

    @FindBy(css = "h1")
    private WebElement headLine;

    private String projectName;
    private int build;

    public ProjectBuildPage(@NotNull WebDriver driver, @NotNull String projectName, int build) {
        super(driver);
        this.projectName = projectName;
        this.build = build;
    }

    public ProjectBuildPage(@NotNull WebDriver driver, boolean checkIfLoaded) {
        super(driver, checkIfLoaded);
    }

    public String getBuildTime() {
        String headLineText = headLine.getText();
        return headLineText.substring(headLineText.indexOf("(") + 1, headLineText.indexOf(")"));
    }

    @Override
    public String getPageUrl() {
        return String.format("http://seltr-kbp1-1.synapse.com:8080/job/%s/%d", MethodsForTests.encode(projectName), build);
    }

    @Override
    protected void verifyUniqueElement() throws Error {
        Assert.assertTrue(driver.findElements(By.cssSelector("#tasks .task-link")).stream()
                .anyMatch(el -> el.getAttribute("href").endsWith("/confirmDelete")));
        Assert.assertThat(By.id("description"), OwnMatchers.presenceOfElementLocatedBy(driver));
    }

    /*protected static ProjectPage createProjectBuildPageWithLoadingValidation(@NotNull WebDriver driver, @NotNull String projectName, int build) {
        return new ProjectPage(driver, true) {
            @Override
            public String getPageUrl() {
                return String.format("http://seltr-kbp1-1.synapse.com:8080/job/%s/%d", MethodsForTests.encode(projectName), build);
            }
        };
    }*/
}
