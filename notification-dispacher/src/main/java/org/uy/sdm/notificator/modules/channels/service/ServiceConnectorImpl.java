package org.uy.sdm.notificator.modules.channels.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class ServiceConnectorImpl implements ServiceConnector {

	@Override
	public <T> void sendMessageToService(String serviceUrl, T messageContent) {
		//TODO: Mandar a otro servicio.
	}
}
