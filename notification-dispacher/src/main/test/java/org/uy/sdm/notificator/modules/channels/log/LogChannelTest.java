package org.uy.sdm.notificator.modules.channels.log;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogChannelTest {

	@InjectMocks
	private LogChannelImpl logChannel;

	@Test
	public void sendTest() {
		final NotificationDto notificationDto = mock(NotificationDto.class);
		logChannel.send(notificationDto);
		verify(notificationDto,atLeastOnce()).getRecipient();
	}

}
