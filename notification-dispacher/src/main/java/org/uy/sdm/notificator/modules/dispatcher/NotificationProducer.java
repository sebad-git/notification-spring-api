package org.uy.sdm.notificator.modules.dispatcher;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;

@Component
@RequiredArgsConstructor
public class NotificationProducer {

	private final RabbitTemplate rabbitTemplate;

	public void send(NotificationDto notificationDto) {
		rabbitTemplate.convertAndSend(
			DispacherRabbitConfig.NOTIFICATION_EXCHANGE,
			DispacherRabbitConfig.NOTIFICATION_ROUTING_KEY,
			notificationDto
		);
	}
}
