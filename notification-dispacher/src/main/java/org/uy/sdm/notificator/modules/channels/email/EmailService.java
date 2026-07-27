package org.uy.sdm.notificator.modules.channels.email;

public interface EmailService {

	void sendEmail(
		final String recipient,
		final String subject,
		final String body);
}
