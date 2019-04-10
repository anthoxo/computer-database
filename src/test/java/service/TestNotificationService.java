package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import main.MainConfig;

public class TestNotificationService {

	NotificationService notificationService;

	@BeforeEach
	public void init() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
		notificationService = context.getBean(NotificationService.class);
		notificationService.clean();
		context.close();
	}

	@Test
	public void testGenerateNotification() {
		assertFalse(this.notificationService.isNotifying());
		this.notificationService.generateNotification("test", this, 0, "this is a test");
		assertEquals(this.notificationService.getLevel(), "test");
		assertEquals(this.notificationService.getMessage(), "this is a test");
		assertTrue(this.notificationService.isNotifying());
	}
}
