package org.uy.sdm.notificator.modules.dispatcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.uy.sdm.notificator.config.DispacherRabbitConfig;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

	private final RabbitTemplate rabbitTemplate;

	public void send(NotificationDto notificationDto) {
		rabbitTemplate.convertAndSend(
			DispacherRabbitConfig.NOTIFICATION_EXCHANGE,
			DispacherRabbitConfig.NOTIFICATION_ROUTING_KEY,
			notificationDto
		);
		log.info("✅ [NotificationProducer]: Notificacion enviada a la cola: [{}].",
			DispacherRabbitConfig.NOTIFICATION_QUEUE
		);
	}
}
