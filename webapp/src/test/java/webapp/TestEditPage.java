package webapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestEditPage {
	@BeforeAll
	public static void setUp() throws IOException {
		/**
		 * You must put your chromedriver in the same level of your repo git
		 */
		System.setProperty("webdriver.chrome.driver", "./../chromedriver");
	}

	@Test
	public void testClickToEdit() {
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:8080/computer");

		WebElement webElement = driver.findElement(By.tagName("tbody"));
		List<WebElement> l = webElement.findElements(By.tagName("tr"));

		l.get(0).findElement(By.tagName("a")).click();
		assertTrue(driver.getCurrentUrl().contains("/computer/edit?id="));

		driver.close();
		driver.quit();
	}

	@Test
	public void testEditHave4Field() {
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:8080/computer/edit?id=1");

		List<WebElement> l = driver.findElements(By.className("form-group"));
		assertEquals(l.size(), 4);

		driver.close();
		driver.quit();
	}
}
