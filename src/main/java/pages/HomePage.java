package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Random;


/**
 * Created by tetiana.sviatska on 6/30/2015.
 */
public class HomePage extends AuthenticationBasePage<HomePage> {

    public static final String HOME_PAGE_URL = "http://seltr-kbp1-1.synapse.com:8080/";

    @FindBy(css = "#projectstatus a.model-link:not([href*=\"last\"])")
    private List<WebElement> projects;

   /* public ProjectPage openProject(){
        Random random = new Random();
        int i = random.nextInt(projects.size());
        String projectName = projects.get(i).getText();
        projects.get(i).click();
        log.info("Click on the project {}", projectName);
        return new ProjectPage(driver, projectName);
    }*/

    public HomePage(WebDriver driver) {
        super(driver, HOME_PAGE_URL);
    }
}
