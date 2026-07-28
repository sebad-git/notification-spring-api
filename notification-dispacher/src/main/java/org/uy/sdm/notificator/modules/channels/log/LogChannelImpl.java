package org.uy.sdm.notificator.modules.channels.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.uy.sdm.notificator.config.DispacherRabbitConfig;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;


@Component
@Slf4j
public class LogChannelImpl implements LogChannel {

	@Override
	public void send(NotificationDto message) {
		try {
			log.info(
				"\uD83D\uDCDD [LogChannel]: Escribiendo log [ Recipient:{}, Subject: {}, Body: {}, Priority:{}]",
				message.getRecipient(),
				message.getSubject(),
				message.getBody(),
				message.getPriority());
			log.info("✅ [LogChannel]: Notificacion:[{}] logueada con exito.",message.getId());
		} catch (Exception e) {
			log.error("Error llamando al servicio externo", e);
			log.error("❌ [LogChannel]: Error logueando notificacion:[{}].",
				DispacherRabbitConfig.NOTIFICATION_QUEUE
			);
			//Relanza la ecepcion para ejecutar reintentos.
			throw e;
		}
	}
}
