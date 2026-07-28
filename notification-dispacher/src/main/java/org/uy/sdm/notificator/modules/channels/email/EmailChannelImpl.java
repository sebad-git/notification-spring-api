package org.uy.sdm.notificator.modules.channels.email;

import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.uy.sdm.notificator.exceptions.EmailValidationException;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;


@Component
@Slf4j
@RequiredArgsConstructor
public class EmailChannelImpl implements EmailChannel {

	@Value("${spring.mail.from:noreply@notificator.local}")
	@Valid
	private String noReply;
	private final JavaMailSender mailSender;

	private static final String EMAIL_REGEX_FORMAT =
		"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

	@Override
	public void send(NotificationDto message) {
		final String recipient = message.getRecipient();
		final String subject = message.getSubject();
		final String body = message.getBody();
		this.validateEmail(recipient,subject,body);
		try {
			log.info("\uD83D\uDCE4 [EmailChannel]: Enviando mensaje a [{}] con el asunto[{}] y body:[{}]",
				recipient, subject, body
			);
			final MimeMessage mimeMessage = mailSender.createMimeMessage();
			final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			helper.setFrom(noReply);
			helper.setTo(recipient);
			helper.setSubject(subject);
			helper.setText(body, false);
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			log.error("❌ [EmailChannel]: Error la notificacion:[{}] por mail",message.getId(), e);
			//Relanza la ecepcion para ejecutar reintentos.
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	private void validateEmail(
		final String recipient,
		final String subject,
		final String body) {
		log.info("\uD83D\uDCCB [EmailChannel]: Validando campos del mensaje");
		try{
			if(StringUtils.isEmpty(recipient) || !recipient.matches(EMAIL_REGEX_FORMAT))
				throw new EmailValidationException("recipient");
			if(StringUtils.isEmpty(subject))
				throw new EmailValidationException("subject");
			if(StringUtils.isEmpty(body))
				throw new EmailValidationException("body");
		}catch (EmailValidationException e) {
			log.info(e.getMessage());
			throw e;
		}
	}
}
