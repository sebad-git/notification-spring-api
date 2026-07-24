package org.uy.sdm.notificator.modules.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;
import org.uy.sdm.notificator.modules.notification.dto.NotificationMapper;
import org.uy.sdm.notificator.modules.notification.model.Notification;
import org.uy.sdm.notificator.modules.notification.model.Status;
import org.uy.sdm.notificator.modules.notification.repo.NotificationRepo;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepo notificationRepo;

	@Override
	public NotificationDto addNotification(NotificationDto notificationDto) {
		Notification notification = NotificationMapper.convert(notificationDto);
		notification.setStatus(Status.RECEIVED);
		notification = notificationRepo.save(notification);
		return NotificationMapper.convert(notification);
	}

}
