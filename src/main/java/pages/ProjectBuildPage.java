package pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import other.MethodsForTests;
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

    /*protected static ProjectPage createProjectBuildPageWithLoadingValidation(@NotNull WebDriver driver, @NotNull String projectName, int build) {
        return new ProjectPage(driver, true) {
            @Override
            public String getPageUrl() {
                return String.format("http://seltr-kbp1-1.synapse.com:8080/job/%s/%d", MethodsForTests.encode(projectName), build);
            }
        };
    }*/
}
