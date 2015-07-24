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
 * Created by tetiana.sviatska on 7/23/2015.
 */

public class ProjectCreatingPage extends AuthenticationBasePage<ProjectCreatingPage> {

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(css = "input[value*=\"FreeStyleProject\"]")
    private WebElement freeStyleRadioButton;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    @FindBy(css = ".error")
    private WebElement errorMessage;

    private String viewName;

    public ProjectCreatingPage(@NotNull WebDriver driver, @NotNull String viewName) {
        super(driver);
        this.viewName = viewName;
    }

    private ProjectCreatingPage(@NotNull WebDriver driver, @NotNull boolean checkIfLoaded) {
        super(driver, checkIfLoaded);
    }

    public ProjectConfigurationPage createFreestyleProject(@NotNull String projectName) throws RuntimeException{
        log.info("Creating Freestyle Project: {}", projectName);
        try {
            nameInput.sendKeys(projectName);
            if(errorMessage.isDisplayed()) throw new RuntimeException("Cannot create project with name: " + projectName);
            freeStyleRadioButton.click();
            okButton.click();
        }
        catch (Exception e){
            throw new RuntimeException("Cannot create Freestyle Project");
        }
        return ProjectConfigurationPage.createProjectConfigurationPageWithLoadingValidation(driver, projectName);
    }

    @Override
    public String getPageUrl() {
        return String.format("http://seltr-kbp1-1.synapse.com:8080/view/%s/newJob", MethodsForTests.encode(viewName));
    }

    @Override
    protected void verifyUniqueElement() throws Error {
        Assert.assertThat(By.cssSelector("form[name=\"createItem\"]"), OwnMatchers.presenceOfElementLocatedBy(driver));
    }

    /*protected static ProjectCreatingPage createProjectCreatingPageWithLoadingValidation(@NotNull WebDriver driver, @NotNull String viewName) {
        return new ProjectCreatingPage(driver, true) {
            @Override
            public String getPageUrl() {
                return String.format("http://seltr-kbp1-1.synapse.com:8080/view/%s/newJob", MethodsForTests.encode(viewName));
            }
        };
    }*/
}
