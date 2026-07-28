package org.uy.sdm.notificator.modules.channels.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.uy.sdm.notificator.ApplicationProperties;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;


@Component
@Slf4j
@RequiredArgsConstructor
public class ServiceChannelImpl implements ServiceChannel {

	private final RestTemplate restTemplate;
	private final ApplicationProperties props;

	/**
	 * Envia la notificacion a otro servicio.
	 * Para prueba le mando el mismo un api-key de esta aunque deberia tenr configurado otro.
	 *
	 * @param message la notificacion.
	 */
	@Override
	public void send(NotificationDto message) {
		final String endpointUrl = props.getReceiverServiceUrl();
		try {
			final HttpHeaders headers = new HttpHeaders();
			headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
			headers.set(ApplicationProperties.API_KEY,this.props.getSecretKey());
			log.info("\uD83D\uDCE4 [ServiceChannel]: Enviando notificacion al servicio:[{}]", endpointUrl);
			restTemplate.postForEntity(endpointUrl, new HttpEntity<>(message, headers), Void.class);
			log.info("✅ [ServiceChannel]: Notificacion enviada al servicio:[{}]", endpointUrl);
		} catch (Exception e) {
			log.error("❌ [ServiceChannel]: Error llamando al servicio:[{}] la notificacion:[{}] no pudo ser enviada."
				,endpointUrl, message.getId(), e
			);
			//Relanza la ecepcion para ejecutar reintentos.
			throw e;
		}
	}
}
