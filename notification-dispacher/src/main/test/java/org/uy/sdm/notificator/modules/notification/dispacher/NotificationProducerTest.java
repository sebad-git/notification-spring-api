package org.uy.sdm.notificator.modules.notification.dispacher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.uy.sdm.notificator.config.DispacherRabbitConfig;
import org.uy.sdm.notificator.modules.dispatcher.NotificationProducer;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class NotificationProducerTest {

	@InjectMocks
	private NotificationProducer notificationProducer;

	@Mock
	private RabbitTemplate rabbitTemplate;

	@Test
	public void sendTest() {
		final NotificationDto notificationDto = new NotificationDto();
		notificationProducer.send(new NotificationDto());
		verify(rabbitTemplate, atLeastOnce()).convertAndSend(
			DispacherRabbitConfig.NOTIFICATION_EXCHANGE,
			DispacherRabbitConfig.NOTIFICATION_ROUTING_KEY,
			notificationDto);
	}

}
