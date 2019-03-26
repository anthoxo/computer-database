package view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import model.Page;

public class TestComputerPage {
	@BeforeAll
	public static void setUp() throws IOException {
		/**
		 * You must put your chromedriver in the same level of your repo git
		 */
		System.setProperty("webdriver.chrome.driver", "./../chromedriver");
	}

	@Test
	public void testGetMainButtons() {
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:8080/computer");

		List<WebElement> l = driver.findElements(By.className("btn"));
		assertTrue(l.size() >= 2);

		driver.close();
		driver.quit();
	}

	@Test
	public void testHaveTenComputers() {
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:8080/computer");

		WebElement webElement = driver.findElement(By.tagName("tbody"));
		List<WebElement> l = webElement.findElements(By.tagName("tr"));

		assertEquals(l.size(), Page.NB_ITEMS_PER_PAGE);

		driver.close();
		driver.quit();
	}

	@Test
	public void testClickOnAddButton() {
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:8080/computer");

		List<WebElement> l = driver.findElements(By.className("btn")).stream().filter((WebElement e) -> {
			return e.getAttribute("id").equals("addComputerBtn");
		}).collect(Collectors.toList());

		assertTrue(l.size() > 0);
		l.get(0).click();
		assertTrue(driver.getCurrentUrl().contains("/computer/add"));

		driver.close();
		driver.quit();
	}

	@Test
	public void testClickOnSearchButton() {
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:8080/computer");

		List<WebElement> l = driver.findElements(By.className("btn")).stream().filter((WebElement e) -> {
			return e.getAttribute("id").equals("searchSubmitBtn");
		}).collect(Collectors.toList());

		assertTrue(l.size() > 0);
		driver.findElement(By.id("searchbox")).sendKeys("Ma");
		l.get(0).click();
		assertTrue(driver.getCurrentUrl().contains("/computer/search"));
		List<WebElement> l2 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

		assertTrue(l2.size() <= Page.NB_ITEMS_PER_PAGE);

		driver.close();
		driver.quit();
	}
}
