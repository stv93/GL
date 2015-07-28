package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import other.OwnMatchers;
import other.Source;

/**
 * Created by tetiana.sviatska on 7/28/2015.
 */
public class SecurityConfigurationPage extends AuthenticationBasePage<SecurityConfigurationPage>{

    @FindBy(id = "slaveAgentPortId")
    private WebElement slaveAgentPortId;

    @FindBy(css = "input[name = \"_.useSecurity\"]")
    private WebElement useSecurityButton;

    @FindBy(id = "yui-gen5-button")
    private WebElement saveButton;

    @FindBy(css = "input[name = \"_.allowsSignup\"]")
    private WebElement allowsSignUp;

    public SecurityConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public void allowsSignUp(){
        log.info("Check checkbox \"{}\"", Source.getValue("SecurityConfigurationAllowsSignUp"));
        if(!allowsSignUp.isSelected()){
            allowsSignUp.click();
        }
        saveButton.click();
    }

    public void forbidsSignUp(){
        log.info("Uncheck checkbox \"{}\"", Source.getValue("SecurityConfigurationAllowsSignUp"));
        if(allowsSignUp.isSelected()){
            allowsSignUp.click();
        }
        saveButton.click();
    }

    @Override
    public String getPageUrl() {
        return "http://seltr-kbp1-1.synapse.com:8080/configureSecurity";
    }

    @Override
    protected void verifyUniqueElement() throws Error {
        Assert.assertThat(slaveAgentPortId, OwnMatchers.presenceOfElement());
        Assert.assertThat(useSecurityButton, OwnMatchers.presenceOfElement());
    }
}
