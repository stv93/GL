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
    public void clearNullListUser(){    	
    	try {
    		MethodsForTests.logInAsAdmin(driver);
    		MethodsForTests.usersClearing(null, driver);
	    }
		catch (NullPointerException e) {
			Assert.assertNull("List is null", e);
		}
    }

//    @Test
//    public void searcUserWithNullList(){    		
//    	try {	 
//    		String expectedResult = MethodsForTests.makeAuthenticatedSession(null);
//    		new LoginPage(driver).get().signIn("admin", "admin").autoCompleteSearch(expectedResult.substring(5), expectedResult);   
//    	}
//    	catch (NullPointerException e) {
//			Assert.assertNull("List is null", e);
//		}
//    }       

//    @Test
//    public void searchUserWithNullDriver(){    		
//    	try {	 
//    		String expectedResult = MethodsForTests.makeAuthenticatedSession(list);
//    	new LoginPage(driver).get().signIn("admin", "admin").autoCompleteSearch(expectedResult.substring(5), expectedResult);  
//	    } 
//		catch (NullPointerException e) {
//			Assert.assertNull("Driver is null", e);
//		}
//    }    
    
    @Test
    public void nullDriver(){
    	try {
    		new LoginPage(null).get();		
		} 
    	catch (NullPointerException e) {
    		Assert.assertNull("Driver is null", e);
    	}
    }   
    
//    @SuppressWarnings("null")
//	@Test
//    public void isOnHomePage(){
//    	List<Page<?>> page = null;
//    	page.add(new HomePage(driver));
//    	
//    	try {
//    		new HomePage(driver).get();
//    	Assert.assertEquals(driver.getCurrentUrl(), HomePage.HOME_PAGE_URL);
//    	}
//    	catch(AssertionError err){    		
//    		Assert.assertNull("Right Page is not loaded" , err);    		
//    	}	
//    }
    
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
