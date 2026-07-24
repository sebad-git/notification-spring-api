package org.uy.sdm.notificator.modules.notification.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;
import org.uy.sdm.notificator.modules.notification.dto.NotificationMapper;
import org.uy.sdm.notificator.modules.notification.model.Channel;
import org.uy.sdm.notificator.modules.notification.model.Priority;
import org.uy.sdm.notificator.modules.notification.repo.NotificationRepo;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

	@InjectMocks
	private NotificationServiceImpl notificationService;

	@Mock
	private NotificationRepo notificationRepo;

	@Test
	public void handleCreateAlertTest() {
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
	}

	//NotificationDto addNotification(NotificationDto notificationDto);

}
