package com.ten31f.app;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {

	public static final String FORMAT = "youtube-dl \"%s\"";

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");

		// Create firfox driver's instance
		WebDriver driver = new ChromeDriver();

		driver.get(args[0]);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		waitForVisiable(driver);

		Set<String> links = new HashSet<>();

		List<WebElement> elements = driver.findElements(By.xpath("//a"));
		for (WebElement element : elements) {
			links.add(element.getAttribute("href"));

		}

		driver.close();

		List<String> linkList = links.stream().filter(link -> link.contains("?") && link.contains("@"))
				.collect(Collectors.toList());

		Collections.sort(linkList);

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("#!/bin/bash").append(System.lineSeparator());
		stringBuilder.append("#").append(System.lineSeparator());
		stringBuilder.append("# Start").append(System.lineSeparator());

		linkList.forEach(link -> stringBuilder.append(String.format(FORMAT, link)).append(System.lineSeparator()));

		stringBuilder.append("# end").append(System.lineSeparator());

		System.out.println(stringBuilder.toString());

	}

	public static void waitForVisiable(WebDriver driver) {

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
