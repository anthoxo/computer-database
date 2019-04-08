package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestNotificationService {

	NotificationService notificationService;

	@BeforeEach
	public void init() {
		notificationService = NotificationService.getInstance();
		notificationService.clean();
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
