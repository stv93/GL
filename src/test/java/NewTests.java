import java.nio.file.InvalidPathException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import other.MethodsForTests;
import other.RandomForPages;
import pages.HomePage;
import pages.LoginPage;
import pages.Page;
import pages.SignUpPage;


public class NewTests {
    private static WebDriver driver;
    
    SignUpPage signUpPage = new SignUpPage(driver);
		
    @AfterClass
    public static void teardown(){
    	driver.quit();
    }
    
    @BeforeClass
    public static void before(){
    	System.setProperty("language", "ru");
    	System.setProperty("browser", "firefox");
    	System.setProperty("log4j.configurationFile","log4j.xml");
    	driver = MethodsForTests.getDriver();
    }    
    
    @Test
    public void makeScreeshotWithIncorrectName(){    	
    	try{
    	MethodsForTests.logInAsAdmin(driver);
    	MethodsForTests.makeScreenshot(RandomForPages.randomStringWithInvalidSymbols(), driver);
    	} catch(InvalidPathException e){
    		Assert.assertNull("Incorrect file name", e);
    	}
    }

    
    @Test
    public void uncorrectStringParameter(){     	
    	try{
    		new String(RandomForPages.randomString(-5));
		}
    	catch (NegativeArraySizeException e){
    		Assert.assertNull("Error string creation", e);
    	}
    }
    
    @Test
    public void incoorectSignUp(){
    	try {
    		new LoginPage(driver).get().incorrectSignIn("admin", "admin").tryAgain();
		} 
    	catch (NoSuchElementException e) {
    		Assert.assertNull("There is no possibility to click element", e);
    	}
    }
    
  
}
