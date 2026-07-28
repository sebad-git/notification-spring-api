package org.uy.sdm.notificator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.uy.sdm.notificator.controllers.NotificationController;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;
import org.uy.sdm.notificator.modules.notification.model.Channel;
import org.uy.sdm.notificator.modules.notification.model.Notification;
import org.uy.sdm.notificator.modules.notification.model.Priority;
import org.uy.sdm.notificator.modules.notification.model.Status;
import org.uy.sdm.notificator.modules.notification.repo.NotificationRepo;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class NotificationDispatcherAppITTest {

	@Autowired
	private NotificationController notificationController;

	@Autowired
	private NotificationRepo notificationRepo;

	@Test
	public void sendLogTest() throws InterruptedException {
		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setRecipient("Recipient");
		notificationDto.setSubject("Test Subject");
		notificationDto.setBody("Test Body");
		notificationDto.setChannel(Channel.LOG.name());
		notificationDto.setPriority(Priority.MEDIUM.name());
		notificationDto.setMetadata(new HashMap<>());
		notificationDto = notificationController.addNotification(notificationDto).getBody();
		assertNotNull(notificationDto);
		Thread.sleep(2 *1000);
		Notification notification = notificationRepo.findById(notificationDto.getId()).orElse(null);
		assertNotNull(notification);
		assertNotEquals(Status.FAILED, notification.getStatus());
	}

	@Test
	public void sendToServiceTest() throws InterruptedException {
		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setRecipient("Recipient");
		notificationDto.setSubject("Test Subject");
		notificationDto.setBody("Test Body");
		notificationDto.setChannel(Channel.SERVICE.name());
		notificationDto.setPriority(Priority.MEDIUM.name());
		notificationDto.setMetadata(new HashMap<>());
		notificationDto = notificationController.addNotification(notificationDto).getBody();
		assertNotNull(notificationDto);
		Thread.sleep(5 *1000);
		Notification notification = notificationRepo.findById(notificationDto.getId()).orElse(null);
		assertNotNull(notification);
		assertNotEquals(Status.FAILED, notification.getStatus());
	}

	@Test
	public void sendEmailTest() throws InterruptedException {
		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setRecipient("test@email.uy");
		notificationDto.setSubject("Test Subject");
		notificationDto.setBody("Test Body");
		notificationDto.setChannel(Channel.EMAIL.name());
		notificationDto.setPriority(Priority.MEDIUM.name());
		notificationDto.setMetadata(new HashMap<>());
		notificationDto = notificationController.addNotification(notificationDto).getBody();
		assertNotNull(notificationDto);
		Thread.sleep(5 *1000);
		Notification notification = notificationRepo.findById(notificationDto.getId()).orElse(null);
		assertNotNull(notification);
		assertNotEquals(Status.FAILED, notification.getStatus());
	}
}
