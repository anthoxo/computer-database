package service;

import javax.management.Notification;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	Notification notification;
	boolean isNotifying;

	private NotificationService() {
		this.isNotifying = false;
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
