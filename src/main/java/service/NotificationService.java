package service;

import javax.management.Notification;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	private Notification notification;
	private boolean isNotifying;

	private NotificationService() {
		this.isNotifying = false;
	}

	public boolean isNotifying() {
		return isNotifying;
	}

	public void generateNotification(String type, Object source, String message) {
		this.isNotifying = true;
		this.notification = new Notification(type, source, 0, message);
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

	public Notification getNotification() {
		return this.notification;
	}
}
