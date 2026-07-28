package org.uy.sdm.notificator.modules.notification.service;

import org.uy.sdm.notificator.modules.dispatcher.NotificationProducer;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;
import org.uy.sdm.notificator.modules.notification.model.Status;

public interface NotificationService {

	/**
	 * Envia la notificacion recibida a la cola via {@link NotificationProducer}.
	 * Si falla la marca como {@link Status#FAILED}.
	 */
	NotificationDto addNotification(NotificationDto notificationDto);

	/**
	 * Actualiza el estado de una {@link org.uy.sdm.notificator.modules.notification.model.Notification} por id.
	 */
	void updateStatus(long notificationId, Status status);

}
