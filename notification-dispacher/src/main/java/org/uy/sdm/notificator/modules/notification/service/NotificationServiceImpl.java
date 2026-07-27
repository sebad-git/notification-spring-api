package org.uy.sdm.notificator.modules.notification.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.stereotype.Service;
import org.uy.sdm.notificator.modules.dispatcher.DispacherRabbitConfig;
import org.uy.sdm.notificator.modules.dispatcher.NotificationProducer;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;
import org.uy.sdm.notificator.modules.notification.dto.NotificationMapper;
import org.uy.sdm.notificator.modules.notification.model.Notification;
import org.uy.sdm.notificator.modules.notification.model.Status;
import org.uy.sdm.notificator.modules.notification.repo.NotificationRepo;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

	private final NotificationProducer notificationProducer;
	private final NotificationRepo notificationRepo;

	@Override
	public NotificationDto addNotification(NotificationDto notificationDto) {
		Notification notification = NotificationMapper.convert(notificationDto);
		notificationRepo.save(notification);
		log.info("✅ [NotificationService]: Notificacion marcada como recibida.");
		NotificationMapper.convert(notification);
		try {
			notificationProducer.send(NotificationMapper.convert(notification));
			notification.setStatus(Status.QUEUED);
			notificationRepo.save(notification);
		}catch (AmqpException | IllegalArgumentException e) {
			log.error("❌ [NotificationService]: Error enviando notificacion a la cola: [{}].",
				DispacherRabbitConfig.NOTIFICATION_QUEUE
			);
			notification.setStatus(Status.FAILED);
			notificationRepo.save(notification);
		}
		return NotificationMapper.convert(notification);
	}

	@Override
	public void updateStatus(long notificationId, Status status) {
		notificationRepo.updateStatus(notificationId,status);
	}

}
