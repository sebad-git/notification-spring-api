package org.uy.sdm.notificator.modules.channels.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.uy.sdm.notificator.ApplicationProperties;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceChannelTest {

	@InjectMocks
	private ServiceChannelImpl serviceChannel;

	@Mock
	private RestTemplate restTemplate;
	@Mock
	private ApplicationProperties props;

	@Test
	public void sendTest() {
		when(props.getReceiverServiceUrl()).thenReturn("url");
		final NotificationDto notificationDto = mock(NotificationDto.class);
		serviceChannel.send(notificationDto);
		verify(restTemplate,atLeastOnce()).postForEntity(anyString(),any(),any());
	}

	@Test
	public void sendTestFail() {
		when(props.getReceiverServiceUrl()).thenReturn("url");
		final NotificationDto notificationDto = mock(NotificationDto.class);
		when(restTemplate.postForEntity(anyString(),any(),any())).thenThrow(RestClientException.class);
		assertThrows(
			RestClientException.class,
			() -> serviceChannel.send(notificationDto)
		);
	}

}
