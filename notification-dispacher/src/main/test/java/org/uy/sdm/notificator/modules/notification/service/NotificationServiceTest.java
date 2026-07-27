package org.uy.sdm.notificator.modules.notification.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.uy.sdm.notificator.modules.dispatcher.NotificationProducer;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;
import org.uy.sdm.notificator.modules.notification.dto.NotificationMapper;
import org.uy.sdm.notificator.modules.notification.model.Channel;
import org.uy.sdm.notificator.modules.notification.model.Priority;
import org.uy.sdm.notificator.modules.notification.repo.NotificationRepo;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

	@InjectMocks
	private NotificationServiceImpl notificationService;

	@Mock
	private NotificationRepo notificationRepo;

	@Mock
	private NotificationProducer notificationProducer;

	@Test
	public void createNotification() {
		final NotificationDto testNotificationDto = new NotificationDto();
		testNotificationDto.setRecipient("Test Recipient");
		testNotificationDto.setSubject("Test Subject");
		testNotificationDto.setChannel(Channel.EMAIL.name());
		testNotificationDto.setBody("Test body");
		testNotificationDto.setPriority(Priority.MEDIUM.name());
		testNotificationDto.setMetadata(new HashMap<>());
		when(notificationRepo.save(any())).thenReturn(NotificationMapper.convert(testNotificationDto));
		NotificationDto savedNotificationDto = notificationService.addNotification(testNotificationDto);
		assertNotNull(savedNotificationDto);
		verify(notificationProducer,atLeastOnce()).send(any());
	}

}
