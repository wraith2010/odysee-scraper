package com.ten31f.app;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CalculatorTest {

	@Test
	// Tests google calculator
	public void googleCalculator() {

		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");

		// Create firfox driver's instance
		WebDriver driver = new ChromeDriver();

		driver.get("https://odysee.com/$/list/a0e04ad05bcf94d6aeb5f2e69b046a7b2f660893");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		waitForVisiable(driver);

		List<WebElement> elements = driver.findElements(By.xpath("//a"));
		for (WebElement element : elements) {
			System.out.println(element.getAttribute("href"));
		}

		driver.close();
	};

	public void waitForVisiable(WebDriver driver) {

		while (driver.findElement(By.className("media__thumb")) == null) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}