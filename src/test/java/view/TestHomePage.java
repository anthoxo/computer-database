package view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestHomePage {

	@BeforeAll
	public static void setUp() throws IOException {
		/**
		 * You must put your chromedriver in the same level of your repo git
		 */
		System.setProperty("webdriver.chrome.driver", "./../chromedriver");
	}

	@BeforeEach
	public void init() {
	}

	@Test
	public void testTwoButtons() {
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:8080/index");

		List<?> l = driver.findElements(By.className("btn"));

		assertEquals(l.size(), 2);

		driver.close();
		driver.quit();
	}
}
