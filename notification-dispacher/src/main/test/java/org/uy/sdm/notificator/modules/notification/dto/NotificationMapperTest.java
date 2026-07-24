package org.uy.sdm.notificator.modules.notification.dto;

import org.junit.jupiter.api.Test;
import org.uy.sdm.notificator.modules.notification.model.Channel;
import org.uy.sdm.notificator.modules.notification.model.Notification;
import org.uy.sdm.notificator.modules.notification.model.Priority;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NotificationMapperTest {

	@Test
	public void mapToEntity() {
		final NotificationDto testNotificationDto = new NotificationDto();
		testNotificationDto.setRecipient("Test Recipient");
		testNotificationDto.setSubject("Test Subject");
		testNotificationDto.setChannel(Channel.EMAIL.name());
		testNotificationDto.setBody("Test body");
		testNotificationDto.setPriority(Priority.MEDIUM.name());
		testNotificationDto.setMetadata(new HashMap<>());
		final Notification notification = NotificationMapper.convert(testNotificationDto);
		assertNotNull(notification);
		assertEquals(Channel.EMAIL,notification.getChannel());
		assertEquals(Priority.MEDIUM,notification.getPriority());
		assertEquals("Test Recipient",notification.getRecipient());
		assertEquals("Test Subject",notification.getSubject());
		assertEquals("Test body",notification.getBody());
	}

	@Test
	public void mapToDto() {
		final Notification testNotification = new Notification();
		testNotification.setRecipient("Test Recipient");
		testNotification.setSubject("Test Subject");
		testNotification.setChannel(Channel.EMAIL);
		testNotification.setBody("Test body");
		testNotification.setPriority(Priority.MEDIUM);
		testNotification.setMetadata(new HashMap<>());
		final NotificationDto notificationDto = NotificationMapper.convert(testNotification);
		assertNotNull(notificationDto);
		assertEquals(Channel.EMAIL.name(),notificationDto.getChannel());
		assertEquals(Priority.MEDIUM.name(),notificationDto.getPriority());
		assertEquals("Test Recipient",notificationDto.getRecipient());
		assertEquals("Test Subject",notificationDto.getSubject());
		assertEquals("Test body",notificationDto.getBody());
	}

}
