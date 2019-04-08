package service;

import javax.management.Notification;

public class NotificationService {

	private static NotificationService instance = null;
	Notification notification;
	boolean isNotifying;

	private NotificationService() {
		this.isNotifying = false;
	}

	public static NotificationService getInstance() {
		if (instance == null) {
			instance = new NotificationService();
		}
		return instance;
	}

	public boolean isNotifying() {
		return isNotifying;
	}

	public void generateNotification(String type, Object source, long sequenceNumber, String message) {
		this.isNotifying = true;
		this.notification = new Notification(type, source, sequenceNumber, message);
	}

	public void clean() {
		this.isNotifying = false;
	}

	public String getMessage() {
		return this.notification.getMessage();
	}

	public String getLevel() {
		return this.notification.getType();
	}
}
