/*
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import other.MethodsForTests;
import other.RandomForPages;
import pages.LoginPage;
import pages.SignUpPage;

import java.nio.file.InvalidPathException;
import java.util.HashSet;
import java.util.Set;


public class NewTests {
	Set<String> list =  new HashSet<>();
	String correctName = RandomForPages.randomString(20);
    String correctEmail = RandomForPages.randomString(5) + "@";
    String incorrectEmail = RandomForPages.randomString(5);
    String password = RandomForPages.randomString(6);
    String incorrectConfirmPassword = RandomForPages.randomString(6);
    private static WebDriver driver;
    
    SignUpPage signUpPage = new SignUpPage(driver);
		
    @AfterClass
    public static void teardown(){
    	driver.quit();
    }
    
    @BeforeClass
    public static void before(){
    	System.setProperty("language", "ru");
    	System.setProperty("browser", "chrome");
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
*/
