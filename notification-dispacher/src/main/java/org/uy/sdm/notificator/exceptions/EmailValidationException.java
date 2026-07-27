package org.uy.sdm.notificator.exceptions;

public class EmailValidationException extends RuntimeException{

	private static final String INVALID_FIELD_MESSAGE_FORMAT = "Error enviando e-mail. El campo [%s] es invalido";

	public EmailValidationException(final String field) {
		super(String.format(INVALID_FIELD_MESSAGE_FORMAT, field));
	}

}
