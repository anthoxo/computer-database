package view;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestComputerPage {
	@BeforeAll
	public static void setUp() throws IOException {
		/**
		 * You must put your chromedriver in the same level of your repo git
		 */
		System.setProperty("webdriver.chrome.driver",
				"./../chromedriver");
	}

	@Test
	public void testGetMainButtons() {
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:8080/computer");

		List<WebElement> l = driver.findElements(By.className("btn"));
		assertTrue(l.size() >= 2);

		boolean findSearchButton = false;
		boolean findAddComputerButton = false;

		for (WebElement e : l) {
			if (e.getAttribute("id").equals("addComputerBtn")) {
				findAddComputerButton = true;
			}
			if (e.getAttribute("id").equals("searchSubmitBtn")) {
				findSearchButton = true;
			}
		}

		assertTrue(findSearchButton);
		assertTrue(findAddComputerButton);

		driver.close();
		driver.quit();
	}

}
