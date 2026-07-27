package org.uy.sdm.notificator.modules.notification.dispacher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.uy.sdm.notificator.modules.channels.email.EmailService;
import org.uy.sdm.notificator.modules.channels.service.ServiceConnector;
import org.uy.sdm.notificator.modules.dispatcher.NotificationDispatcher;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;
import org.uy.sdm.notificator.modules.notification.model.Channel;
import org.uy.sdm.notificator.modules.notification.model.Priority;
import org.uy.sdm.notificator.modules.notification.service.NotificationService;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class NotificationDispatcherTest {

	@InjectMocks
	private NotificationDispatcher notificationDispatcher;

	@Mock
	private EmailService emailService;
	@Mock
	private ServiceConnector serviceConnector;
	@Mock
	private NotificationService notificationService;

	private static final NotificationDto testNotificationDto = new NotificationDto();;

	@BeforeEach
	public void setup(){
		testNotificationDto.setId(1L);
		testNotificationDto.setRecipient("Test Recipient");
		testNotificationDto.setSubject("Test Subject");
		testNotificationDto.setChannel(Channel.EMAIL.name());
		testNotificationDto.setBody("Test body");
		testNotificationDto.setPriority(Priority.HIGH.name());
		testNotificationDto.setMetadata(new HashMap<>());
	}

	@Test
	public void onLogNotificationReceivedTest() {
		testNotificationDto.setChannel(Channel.LOG.name());
		notificationDispatcher.onMessageReceived(testNotificationDto);
		verify(notificationService,atLeastOnce()).updateStatus(anyLong(),any());
	}

	@Test
	public void onEmailNotificationReceivedTest() {
		testNotificationDto.setChannel(Channel.EMAIL.name());
		notificationDispatcher.onMessageReceived(testNotificationDto);
		verify(notificationService,atLeastOnce()).updateStatus(anyLong(),any());
	}

	@Test
	public void onServiceNotificationReceivedTest() {
		testNotificationDto.setChannel(Channel.SERVICE.name());
		notificationDispatcher.onMessageReceived(testNotificationDto);
		verify(notificationService,atLeastOnce()).updateStatus(anyLong(),any());
	}

}
