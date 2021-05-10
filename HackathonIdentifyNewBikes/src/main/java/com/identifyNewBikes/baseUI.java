package com.identifyNewBikes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class baseUI {
	//Initializing web elements using page factory
	public static ExtentReports report = ExtentReportManager.getReportInstance();
	public static ExtentTest logger;
	public static SoftAssert softassert = new SoftAssert();
	public static WebDriver driver;
	@FindBy(xpath = "/html/body/header/div/div[2]/div/div[2]/ul/li[5]/a")
	public static WebElement usedCarsLink;
	@FindBy(xpath = "/html/body/header/div/div[2]/div/div[2]/ul/li[5]/ul/li/div[1]/ul/li[1]/a")
	public static WebElement usedCarsButton;
	@FindBy(xpath = "//*[@id='popularCityList']/li//*[contains(text(),'Chennai ')]")
	public static WebElement location;
	@FindBy(xpath = "/html/body/div[11]/div/div[1]/div[1]/div[1]/div[2]/ul/li[2]/div[2]/div[4]/ul/li")
	public static List<WebElement> popularbrandslist;
	@FindBy(xpath = "//*[@id='headerNewNavWrap']/div[2]/ul/li[3]/*[contains(text(),'New Bikes')]")
	public static WebElement newbikesLink;
	@FindBy(xpath = "//*[@id='headerNewNavWrap']/div[2]/ul/li[3]/ul/li//*[contains(text(),'Upcoming Bikes')]")
	public static WebElement upcomingbikesLink;
	@FindBy(xpath = "//*[@id='makeId']")
	public static WebElement manufacturerListbox;
	@FindBy(xpath = "//*[@id=\"modelList\"]/li//span[contains(text(),'View More Bikes')]")
	public static WebElement viewmoreButton;
	@FindBy(xpath = "//*[@id='carModels']/ul")
	public static WebElement bikesList;
	@FindBy(xpath = "//*[@id=\"des_lIcon\"]")
	public static WebElement login;
	@FindBy(xpath = "//*[text()=('Continue with Google')]")
	public static WebElement continueWithGoogleButton;
	@FindBy(xpath = "//*[@id=\"identifierId\"]")
	public static WebElement emailTextbox;
	@FindBy(xpath = "//*[@id=\"identifierNext\"]/div/button/div[2]")
	public static WebElement nextButton;
	@FindBy(xpath = "//*[@id=\"view_container\"]/div/div/div[2]/div/div[1]/div/form/span/section/div/div/div[1]/div/div[2]/div[2]/div")
	public static WebElement alertMessage;

	// Setting up driver
	public static WebDriver browserSetUp() {
		driver = baseUI.initializeBrowser("Google Chrome");
		System.out.println("Browser Initialized");
		driver.get(baseUI.getProperties("url"));
		softassert.assertEquals(driver.getTitle(),
				"ZigWheels - New Cars, Used Cars, Bikes Prices, News, Reviews, Forum");
		driver.manage().window().maximize();
		baseUI.closeAd(driver);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(120, TimeUnit.SECONDS);
		softassert.assertAll();
		return driver;
	}

	// Initialising browser
	public static WebDriver initializeBrowser(String browser)// initializing browser
	{

		if (browser.equalsIgnoreCase("Google Chrome")) {
			System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			driver = new ChromeDriver(options);
		}
		if (browser.equalsIgnoreCase("Mozilla Firefox")) {
			System.setProperty("webdriver.gecko.driver", "Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		return driver;
	}

	// getting value from properties file
	public static String getProperties(String key) {
		Properties props = new Properties();
		FileInputStream input;
		try {
			input = new FileInputStream("src\\test\\resources\\config.properties");
			props.load(input);
		} catch (Exception e) {

			e.printStackTrace();
		}

		String locator = props.getProperty(key);
		return locator;

	}

	// Closing ad if appears
	public static void closeAd(WebDriver driver) {
		boolean eleSelected = existsElement(getProperties("ad_Property"));
		if (eleSelected == true) {
			driver.findElement(By.xpath(getProperties("ad_Property"))).click();
		}
	}

	public static boolean existsElement(String xpath) {

		try {
			driver.findElement(By.xpath(xpath));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// Taking screenshots
	public static void takeScreenShots(WebDriver driver) {
		try {
			TakesScreenshot scrShot = ((TakesScreenshot) driver);
			File source = scrShot.getScreenshotAs(OutputType.FILE);
			File dest = new File("src\\test\\resources\\Screenshots\\" + DateUtil.getTimeStamp() + ".png");
			FileUtils.copyFile(source, dest);
			System.out.println("Screenshot is taken");

		} catch (Exception e) {

			System.out.println(e);
		}
	}

	// Taking screenshots if the test got failed
	public static void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenShots(driver);
		try {
			logger.addScreenCaptureFromPath("src\\test\\resources\\Screenshots\\" + DateUtil.getTimeStamp() + ".png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);
	}

	// closing the driver
	public static void closeDriver(WebDriver driver) {
		driver.close();
		report.flush();
	}

	// quitting the browser
	public static void quitDriver(WebDriver driver) {
		driver.quit();
	}

}
