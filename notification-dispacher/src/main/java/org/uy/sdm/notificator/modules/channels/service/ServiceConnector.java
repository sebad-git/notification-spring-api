package org.uy.sdm.notificator.modules.channels.service;

public interface ServiceConnector {

	<T> void sendMessageToService(final String serviceUrl, final T messageContent);

}
