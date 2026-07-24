package org.uy.sdm.notificator.modules.notification.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.uy.sdm.notificator.modules.dispatcher.NotificationProducer;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;
import org.uy.sdm.notificator.modules.notification.dto.NotificationMapper;
import org.uy.sdm.notificator.modules.notification.model.Notification;
import org.uy.sdm.notificator.modules.notification.model.Status;
import org.uy.sdm.notificator.modules.notification.repo.NotificationRepo;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final NotificationProducer notificationProducer;
	private final NotificationRepo notificationRepo;

	@Override
	public NotificationDto addNotification(NotificationDto notificationDto) {
		Notification notification = NotificationMapper.convert(notificationDto);
		notification.setStatus(Status.RECEIVED);
		notificationRepo.save(notification);
		NotificationMapper.convert(notification);
		try {
			notificationProducer.send(NotificationMapper.convert(notification));
			notification.setStatus(Status.QUEUED);
			notificationRepo.save(notification);
		}catch (Exception e) {
			notification.setStatus(Status.FAILED);
			notificationRepo.save(notification);
		}
		return NotificationMapper.convert(notification);
	}

}
