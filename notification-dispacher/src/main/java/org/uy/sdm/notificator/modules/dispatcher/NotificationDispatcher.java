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

	@Retryable(
		retryFor = { Exception.class },
		maxAttempts = MAX_ATTEMPTS,
		backoff = @Backoff(delay = DELAY)
	)
	@RabbitListener(queues = DispacherRabbitConfig.NOTIFICATION_QUEUE)
	public void onMessageReceived(final NotificationDto notificationDto) {
		log.info("[NotificationDispatcher]: Procesando notificación id={}", notificationDto.getId());
		dispatchNotification(notificationDto);
	}

	private void dispatchNotification(final NotificationDto notificationDto) {
		final Channel channel = Channel.valueOf(notificationDto.getChannel());
		switch (channel) {
			case Channel.EMAIL -> emailService.send(notificationDto);
			case Channel.LOG -> logChannel.send(notificationDto);
			case Channel.SERVICE -> serviceConnector.send(notificationDto);
			default -> log.info("Canal no soportado: {}",channel);
		}
		notificationService.updateStatus(notificationDto.getId(),Status.DELIVERED);
	}

	@Recover
	public void recover(Exception ex, NotificationDto notificationDto) {
		log.error("❌ [NotificationDispatcher]: La notificacion [{}] no pudo ser eviada despues del maximo de reintentos ({}).",
			notificationDto.getId(),
			MAX_ATTEMPTS,
			ex
		);
		notificationService.updateStatus(notificationDto.getId(),Status.FAILED);
	}
}
