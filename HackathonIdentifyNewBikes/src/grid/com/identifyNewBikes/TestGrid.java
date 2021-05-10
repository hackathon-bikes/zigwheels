package com.identifyNewBikes;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
//testing using selenium grid
public class TestGrid extends baseUI {
	public static RemoteWebDriver driver1;
	@BeforeClass
	@Parameters({ "browser" })
	//initialising remote web driver
	public static void init(String browser) {
		DesiredCapabilities capabilities = null;

		if (browser.equalsIgnoreCase("firefox")) {
			capabilities = DesiredCapabilities.chrome();

		}
		if (browser.equalsIgnoreCase("chrome")) {
			capabilities = DesiredCapabilities.chrome();

		}
		try {
			driver1 = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver1.get(baseUI.getProperties("url"));
		driver1.manage().window().maximize();
		driver1.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		driver1.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver1.manage().timeouts().setScriptTimeout(120, TimeUnit.SECONDS);
		baseUI base = new baseUI();
		PageFactory.initElements(driver1, base);

	}
   
	@Test(priority = 1)
	//upcoming Bikes test
	public static void upcomingBikesTest() throws Exception {
		driver1.navigate().refresh();
		UpcomingBikes.findingUpcomingBikes(driver1);
	}

	@Test(priority = 2)
	//Popular used cars test
	public static void popularUsedCarsTest() throws Exception {
		popularUsedCars.popularUsedCarsModel(driver1);

	}

	@Test(priority = 3)
	//sign in test
	public static void ZigwheelsSignInTest() throws Exception {
		driver1.navigate().to("https://www.zigwheels.com");
		ZigWheelsSignIn.googleSignIn(driver1);
	}

	@AfterClass
	//closing the browser
	public static void closeDriver() {
		driver1.close();
		driver1.quit();

	}

}
