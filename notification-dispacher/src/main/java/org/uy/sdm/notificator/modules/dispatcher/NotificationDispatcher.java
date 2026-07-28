package org.uy.sdm.notificator.modules.dispatcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.uy.sdm.notificator.config.DispacherRabbitConfig;
import org.uy.sdm.notificator.modules.channels.email.EmailChannel;
import org.uy.sdm.notificator.modules.channels.log.LogChannel;
import org.uy.sdm.notificator.modules.channels.service.ServiceChannel;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;
import org.uy.sdm.notificator.modules.notification.model.Channel;
import org.uy.sdm.notificator.modules.notification.model.Status;
import org.uy.sdm.notificator.modules.notification.service.NotificationService;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationDispatcher {

	private static final int MAX_ATTEMPTS = 2;
	private static final int DELAY = 2 * 1000;

	private final EmailChannel emailService;
	private final ServiceChannel serviceConnector;
	private final LogChannel logChannel;

	private final NotificationService notificationService;

	/**
	 * Listener de RabbitMQ con 2 reinitentos.
	 * Considere usar una DLQ para reintentos pero para este caso @Retryable alcanza.
	 *
	 * @param notificationDto los datos de la notificacion.
	 */
	@Retryable(
		retryFor = { Exception.class },
		maxAttempts = MAX_ATTEMPTS,
		backoff = @Backoff(delay = DELAY)
	)
	@RabbitListener(queues = DispacherRabbitConfig.NOTIFICATION_QUEUE)
	public void onMessageReceived(final NotificationDto notificationDto) {
		log.info("\uD83D\uDCE5 [NotificationDispatcher]: Notificacion:[{}] recibida", notificationDto.getId());
		dispatchNotification(notificationDto);
	}

	/**
	 * Procesa las notificaciones por diferentes canales.
	 * Los canales heredan de ina interfaz channel para agregar mas en un futuro
	 * y esta tipado para que no solo procese notificaciones.
	 *
	 * @param notificationDto los datos de la notificacion.
	 */
	private void dispatchNotification(final NotificationDto notificationDto) {
		log.info("\uD83D\uDCDF [NotificationDispatcher]: Procesando notificacion:[{}]", notificationDto.getId());
		final Channel channel = Channel.valueOf(notificationDto.getChannel());
		switch (channel) {
			case Channel.EMAIL -> emailService.send(notificationDto);
			case Channel.LOG -> logChannel.send(notificationDto);
			case Channel.SERVICE -> serviceConnector.send(notificationDto);
			default -> log.info("Canal no soportado: {}",channel);
		}
		log.info("✅ [NotificationDispatcher]: Notificacion despachada con exito:[{}]", notificationDto.getId());
		notificationService.updateStatus(notificationDto.getId(),Status.DELIVERED);
	}

	/**
	 * Metodo para manejar la notificacion cuando se acaban los reintentos
	 * y marcar la notificacion como fallida.
	 *
	 * @param ex La ultima ecepcion.
	 * @param notificationDto los datos de la notificacion.
	 */
	@Recover
	public void recover(Exception ex, NotificationDto notificationDto) {
		log.error("❌ [NotificationDispatcher]: La notificacion:[{}] no pudo ser eviada despues del maximo de reintentos ({}).",
			notificationDto.getId(),
			MAX_ATTEMPTS,
			ex
		);
		notificationService.updateStatus(notificationDto.getId(),Status.FAILED);
	}
}
