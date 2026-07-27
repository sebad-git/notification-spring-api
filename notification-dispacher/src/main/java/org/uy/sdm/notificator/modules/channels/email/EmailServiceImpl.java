package org.uy.sdm.notificator.modules.channels.email;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.uy.sdm.notificator.exceptions.EmailValidationException;


@Service
@Transactional(Transactional.TxType.NEVER)
@Slf4j
public class EmailServiceImpl implements EmailService {

	@Override
	public void sendEmail(String recipient, String subject, String body) {
		log.info("Enviando mensaje a [{}] con el asunto[{}] y body:[{}]",
			recipient,subject,body
		);
		this.validateEmail(recipient, subject, body);
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
