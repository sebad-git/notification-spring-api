package org.uy.sdm.notificator.modules.channels.service;

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
public class ServiceChannelImpl implements ServiceChannel {

	private final RestTemplate restTemplate;
	private final HttpHeaders headers;
	private final ApplicationProperties props;

	public ServiceChannelImpl(final ApplicationProperties applicationProperties, final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.props = applicationProperties;
		this.headers = new HttpHeaders();
		this.headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		this.headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		headers.set(ApplicationProperties.API_KEY,this.props.getSecretKey());
	}

	@Override
	public void send(NotificationDto message) {
		final String endpointUrl = props.getReceiverServiceUrl();
		try {
			log.info("[ServiceChannel]: Enviando notificación al servicio:[{}]", endpointUrl);
			restTemplate.postForEntity(endpointUrl, new HttpEntity<>(message, headers), Void.class);
			log.info("✅ [ServiceChannel]: Notificación enviada al servicio:[{}]", endpointUrl);
		} catch (Exception e) {
			log.error("❌ [ServiceChannel]: Error llamando al servicio:[{}]",endpointUrl, e);
			//Relanza la ecepcion para ejecutar reintentos.
			throw e;
		}
	}
}
