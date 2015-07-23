package pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import other.MethodsForTests;

/**
 * Created by tetiana.sviatska on 7/23/2015.
 */
public class ProjectConfigurationPage extends AuthenticationBasePage<ProjectConfigurationPage> {

    @FindBy(id = "yui-gen38-button")
    private WebElement saveButton;

    @FindBy(name = "name")
    private WebElement nameField;

    private String projectName;

    private ProjectConfigurationPage(@NotNull WebDriver driver, @NotNull boolean checkIfLoaded) {
        super(driver, checkIfLoaded);
    }

    public ProjectPage saveProject(@NotNull String projectName){
        log.info("Saving project: {}", projectName);
        if(nameField.getAttribute("value").equals(projectName)) saveButton.click();
        else throw new RuntimeException("Names didn't match");
        return ProjectPage.createProjectPageWithLoadingValidation(driver,projectName);
    }

    @Override
    public String getPageUrl() {
        return String.format("http://seltr-kbp1-1.synapse.com:8080/job/%s/configure", MethodsForTests.encode(projectName));
    }

    @Override
    protected void verifyUniqueElement() throws Error {
    }

    protected static ProjectConfigurationPage createProjectConfigurationPageWithLoadingValidation(@NotNull WebDriver driver, @NotNull String projectName) {
        return new ProjectConfigurationPage(driver, true) {
            @Override
            public String getPageUrl() {
                return String.format("http://seltr-kbp1-1.synapse.com:8080/job/%s/configure", MethodsForTests.encode(projectName));
            }
        };
    }
}
