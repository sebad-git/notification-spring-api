package org.uy.sdm.notificator.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.uy.sdm.notificator.exceptions.EmailValidationException;

import java.util.Collection;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Collection<String>> handleArgumentNotValidException(MethodArgumentNotValidException ex) {
		final Collection<String> messages = ex.getBindingResult().getFieldErrors()
			.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
		log.error(messages.toString(),ex);
		return ResponseEntity.badRequest().body(messages);
	}

	@ExceptionHandler(EmailValidationException.class)
	public ResponseEntity<String> handleEmailValidationException(EmailValidationException ex) {
		log.error(ex.getMessage(),ex);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception ex) {
		final String errorMessage = String.format("Ha ocurrido un error desconocido: [%s]. Intente de nuevo mas tarde.",ex.getMessage());
		log.error(errorMessage,ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(String.format(errorMessage,ex.getMessage()));
	}

}
