package org.uy.sdm.notificator.modules.channels.email;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.uy.sdm.notificator.exceptions.EmailValidationException;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;


@Component
@Slf4j
public class EmailChannelImpl implements EmailChannel {

	@Override
	public void send(NotificationDto message) {
		final String recipient = message.getRecipient();
		final String subject = message.getSubject();
		final String body = message.getBody();

		log.info("Enviando mensaje a [{}] con el asunto[{}] y body:[{}]",recipient, subject,body);
		this.validateEmail(recipient,subject,body);
		//TODO: ENVIAR MAIL.

	}

	private void validateEmail(
		final String recipient,
		final String subject,
		final String body) {
		if(StringUtils.isEmpty(recipient))
			throw new EmailValidationException("recipient");
		if(StringUtils.isEmpty(subject))
			throw new EmailValidationException("subject");
		if(StringUtils.isEmpty(body))
			throw new EmailValidationException("body");
	}

}
