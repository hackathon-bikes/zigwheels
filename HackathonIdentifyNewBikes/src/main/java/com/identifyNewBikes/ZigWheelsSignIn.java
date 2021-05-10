package com.identifyNewBikes;

import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.Status;

public class ZigWheelsSignIn extends baseUI {// invoking below mwthods
	public static void googleSignIn(WebDriver driver) {
		try {
			logger = report.createTest("Sign In");
			driver = clickLogin(driver);
			logger.log(Status.INFO, "login is clicked");
			fillLoginForm(driver);
			logger.log(Status.INFO, "Invalid username has been entered");
			getErrorMessage(driver);
			reportPass("Alert message is displayed");
			softassert.assertAll();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	// clicking on login
	public static WebDriver clickLogin(WebDriver driver) throws InterruptedException {
		login.click();
		Thread.sleep(5000);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(continueWithGoogleButton)).click();
		String parent = driver.getWindowHandle();
		Set<String> s1 = driver.getWindowHandles();

		Iterator<String> I1 = s1.iterator();

		while (I1.hasNext()) {

			String child_window = I1.next();

			if (!parent.equals(child_window)) {
				driver.switchTo().window(child_window);
			}
		}
		driver.manage().window().maximize();
		return driver;
	}

	// filling the form
	public static void fillLoginForm(WebDriver driver) {
		emailTextbox.sendKeys(baseUI.getProperties("email"));
		nextButton.click();
		System.out.println("Invalid Username is entered");
	}

	// verifying and displaying error message
	public static void getErrorMessage(WebDriver driver) {
		String ErrMessage = alertMessage.getText();
		boolean error = alertMessage.isDisplayed();
		System.out.println("*****************************************************************************");
		System.out.println("Alert message is displayed");
		System.out.println("*****************************************************************************");
		softassert.assertTrue(error);
		// taking screenshot
		baseUI.takeScreenShots(driver);
		System.out.print("Google Sign-In Error Message is displayed as:  ");
		System.out.println(ErrMessage);
	}

}
