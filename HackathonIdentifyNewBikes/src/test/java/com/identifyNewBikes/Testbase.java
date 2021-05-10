package com.identifyNewBikes;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class Testbase extends baseUI {
	public static ExtentReports report = ExtentReportManager.getReportInstance();// creating report instance
	public static ExtentTest logger;

	@BeforeClass(groups = "smoke")
	// initializing browser and page factory elements
	public static void init() {
		baseUI.browserSetUp();
		baseUI base = new baseUI();
		PageFactory.initElements(driver, base);

	}

	@Test(priority = 1, groups = "smoke")
	// UpcomingBikes test method
	public static void upcomingBikesTest() throws Exception {
		UpcomingBikes.findingUpcomingBikes(driver);
	}

	@Test(priority = 2, groups = { "regression" })
	// popularUsedCarsTestMethod
	public static void popularUsedCarsTest() throws Exception {
		popularUsedCars.popularUsedCarsModel(driver);

	}

	@Test(priority = 3, groups = { "smoke", "regression" })
	// SignIn test method
	public static void ZigwheelsSignInTest() throws Exception {
		driver.navigate().to("https://www.zigwheels.com");
		ZigWheelsSignIn.googleSignIn(driver);
	}

	@AfterClass(groups = "smoke")
	// closing and quitting driver
	public static void closeDriver() {
		baseUI.closeDriver(driver);
		baseUI.quitDriver(driver);

	}

}
