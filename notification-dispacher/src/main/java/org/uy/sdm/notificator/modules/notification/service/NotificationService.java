package org.uy.sdm.notificator.modules.notification.service;

import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;
import org.uy.sdm.notificator.modules.notification.model.Status;

public interface NotificationService {

	NotificationDto addNotification(NotificationDto notificationDto);

	void updateStatus(long notificationId, Status status);

}
