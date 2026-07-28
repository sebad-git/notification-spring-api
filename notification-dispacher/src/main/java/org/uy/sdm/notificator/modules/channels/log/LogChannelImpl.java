package org.uy.sdm.notificator.modules.channels.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;


@Component
@Slf4j
public class LogChannelImpl implements LogChannel {

	@Override
	public void send(NotificationDto message) {
		try {
			log.info(
				"✅ [LogChannel]: Logueando notificacion LOG | to={} | subject={} | body={} | priority={}",
				message.getRecipient(),
				message.getSubject(),
				message.getBody(),
				message.getPriority());
		} catch (Exception e) {
			log.error("Error llamando al servicio externo", e);
			//Relanza la ecepcion para ejecutar reintentos.
			throw e;
		}
	}
}
