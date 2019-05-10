package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestNotificationService {

	NotificationService notificationService;

	@BeforeEach
	public void init() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServiceConfig.class);
		notificationService = context.getBean(NotificationService.class);
		notificationService.clean();
		context.close();
	}

	@Test
	public void testGenerateNotification() {
		assertFalse(this.notificationService.isNotifying());
		this.notificationService.generateNotification("test", this, "this is a test");
		assertEquals("test", this.notificationService.getLevel());
		assertEquals("this is a test", this.notificationService.getMessage());
		assertTrue(this.notificationService.isNotifying());
	}
}
