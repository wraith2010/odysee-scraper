package com.ten31f.tools;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class Scrapper {

	private WebDriver webDriver;
	private String URL;
	private String tag;
	private Set<String> links = null;
	private boolean tagFilter = true;
	private boolean inverseTagFilter = false;

	private static Logger logger = Logger.getLogger(Scrapper.class.getName());

	public Scrapper(String URL) {
		setURL(URL);
	}

	public void scrap() {

		System.out.println(String.format("Logger level: %s", logger.getLevel()));

		logger.log(Level.INFO, String.format("Scraping:\t%s", getURL()));

		initWebDriver();

		getWebDriver().get(getURL());
		getWebDriver().manage().timeouts().setScriptTimeout(10, TimeUnit.MINUTES);
		getWebDriver().manage().window().maximize();

		wait(1000);

		while (checkattempt()) {
		}

		getWebDriver().close();

		printScript(links);

	}

	public abstract boolean checkattempt();

	public abstract String getLinkPrintFormat();

	protected void scrollDown() {

		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) getWebDriver();

		javascriptExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	protected Set<String> fetchLinks() {

		List<WebElement> elements = getWebDriver().findElements(By.xpath("//a"));

		Set<String> links = new HashSet<>();

		for (WebElement webElement : elements) {
			try {

				links.add(webElement.getAttribute("href"));

			} catch (StaleElementReferenceException exception) {

				logger.log(Level.INFO, "Stale Refernce exception");

			}
		}

		return filterSet(links);
	}

	private void initWebDriver() {
		setWebDriver(new ChromeDriver());
	}

	protected String getUserTag() {

		int startIndex = getURL().indexOf('@');

		if (startIndex == -1)
			return null;

		String tag = getURL().substring(startIndex);

		int endIndex = tag.indexOf('/');

		if (endIndex > 0) {
			tag = tag.substring(0, endIndex);
		}

		endIndex = tag.indexOf(':');

		if (endIndex > 0) {
			tag = tag.substring(0, endIndex);
		}

		return tag;
	}

	private Set<String> filterSet(Set<String> links) {

		if (isTagFilter()) {
			return links.stream().filter(link -> link != null).filter(link -> link.contains(getTag()))
					.collect(Collectors.toSet());
		} else if (isInverseTagFilter()) {
			return links.stream().filter(link -> link != null).filter(link -> !link.contains("@"))
					.collect(Collectors.toSet());
		}

		return links;

	}

	private static void wait(int mills) {

		try {
			Thread.sleep(mills);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void printScript(Set<String> links) {

		List<String> linkList = links.stream().collect(Collectors.toList());

		Collections.sort(linkList);

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("#!/bin/bash").append(System.lineSeparator());
		stringBuilder.append("#").append(System.lineSeparator());
		stringBuilder.append("# Start").append(System.lineSeparator());

		linkList.forEach(
				link -> stringBuilder.append(String.format(getLinkPrintFormat(), link)).append(System.lineSeparator()));

		stringBuilder.append("# end").append(System.lineSeparator());

		System.out.println(stringBuilder.toString());
	}

	private WebDriver getWebDriver() {
		return webDriver;
	}

	private void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
		setTag(getUserTag());
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Set<String> getLinks() {
		return links;
	}

	public void setLinks(Set<String> links) {
		this.links = links;
	}

	public void setTagFilter(boolean tagFilter) {
		this.tagFilter = tagFilter;
	}

	public boolean isTagFilter() {
		return tagFilter;
	}

	public boolean isInverseTagFilter() {
		return inverseTagFilter;
	}

	public void setInverseTagFilter(boolean inverseTagFilter) {
		this.inverseTagFilter = inverseTagFilter;
	}

}
