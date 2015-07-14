package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import other.MethodsForTests;

import java.util.List;
import java.util.Random;

/**
 * Created by tetiana.sviatska on 7/13/2015.
 */
public class ProjectBuildPage extends AuthenticationBasePage<ProjectBuildPage> {

    public static final String PROJECT_BUILD_PAGE_URL = "http://seltr-kbp1-1.synapse.com:8080/job/%s/%d";

    @FindBy(css = "h1")
    private WebElement headLine;

    public ProjectBuildPage(WebDriver driver, String projectName, int build) {
        super(driver, String.format(PROJECT_BUILD_PAGE_URL, MethodsForTests.encode(projectName), build));
    }

    public String getBuildTime() {
        String headLineText = headLine.getText();
        return headLineText.substring(headLineText.indexOf("(") + 1, headLineText.indexOf(")"));
    }


}
