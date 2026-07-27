package org.uy.sdm.notificator.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.uy.sdm.notificator.exceptions.EmailValidationException;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ControllerExceptionHandlerTest {

	@InjectMocks
	private ControllerExceptionHandler controllerExceptionHandler;

	@Test
	public void handleArgumentNotValidExceptionTest() {
		final MethodArgumentNotValidException exception = mock(
			MethodArgumentNotValidException.class
		);
		final BindingResult bindingResult = mock(BindingResult.class);
		when(exception.getBindingResult()).thenReturn(bindingResult);
		final FieldError error = mock(FieldError.class);
		when(bindingResult.getFieldErrors()).thenReturn(List.of(error));
		when(error.getDefaultMessage()).thenReturn("TestField Error");
		final ResponseEntity<Collection<String>> response =
			controllerExceptionHandler.handleArgumentNotValidException(exception);
		assertNotNull(response);
	}

	@Test
	public void handleEmailValidationExceptionTest() {
		final EmailValidationException exception = new EmailValidationException("subject");
		final ResponseEntity<String> response =
			controllerExceptionHandler.handleEmailValidationException(exception);
		assertNotNull(response);
		assertNotNull(response.getBody());
	}

	@Test
	public void handleGenericExceptionTest() {
		final Exception exception = new Exception("generic");
		final ResponseEntity<String> response =
			controllerExceptionHandler.handleGenericException(exception);
		assertNotNull(response);
		assertNotNull(response.getBody());
	}


}
