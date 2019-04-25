package webapp;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestDeletePage {
	@BeforeAll
	public static void setUp() throws IOException {
		/**
		 * You must put your chromedriver in the same level of your repo git
		 */
		System.setProperty("webdriver.chrome.driver", "./../chromedriver");
	}

	@Test
	public void testClickOnBin() {
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:8080/computer");

		WebElement webElement = driver.findElement(By.tagName("tbody"));
		List<WebElement> l = webElement.findElements(By.tagName("tr"));

		l.get(0).findElement(By.className("btn")).click();

		l = driver.findElements(By.className("modal"));

		// find a way to say Selenium that there is a displyed modal.

		driver.close();
		driver.quit();
	}

}
