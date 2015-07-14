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
public class ProjectPage extends AuthenticationBasePage<ProjectPage> {

    public static final String PROJECT_PAGE_URL = "http://seltr-kbp1-1.synapse.com:8080/job/%s/";
    private String projectName;

    @FindBy(css = ".build-row [class*=\"zws-inserted\"]")
    private List<WebElement> builds;

    public ProjectPage(WebDriver driver, String projectName) {
        super(driver, String.format(PROJECT_PAGE_URL, MethodsForTests.encode(projectName)));
        this.projectName = projectName;
    }

   /* public ProjectBuildPage openBuild(){
        Random random = new Random();
        int i = random.nextInt(builds.size());
        int buildNumber = Integer.parseInt(builds.get(i).getText().substring(builds.get(i).getText().indexOf("#") + 1));
        builds.get(i).click();
        log.info("Click on the build {}", buildNumber);
        return new ProjectBuildPage(driver, projectName, buildNumber);
    }*/

}
