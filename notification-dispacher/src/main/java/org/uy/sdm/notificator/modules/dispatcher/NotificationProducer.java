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

	/**
	 * Envia las notificaciones a una cola de mensajes.
	 * Queria usar Kafka pero use RabbitMQ por familiaridad.
	 * Otras opciones hubieran sido un TreadPoolExcecutor pero afecta el degradamiento.
	 * Considere TreadPoolExcecutor para evitar mas pods que en ciertas nubes aumenta el costo.
	 */
	private final RabbitTemplate rabbitTemplate;

	public void send(NotificationDto notificationDto) {
		rabbitTemplate.convertAndSend(
			DispacherRabbitConfig.NOTIFICATION_EXCHANGE,
			DispacherRabbitConfig.NOTIFICATION_ROUTING_KEY,
			notificationDto
		);
		log.info("\uD83D\uDCE4 [NotificationProducer]: Notificacion enviada a la cola: [{}].",
			DispacherRabbitConfig.NOTIFICATION_QUEUE
		);
	}
}
