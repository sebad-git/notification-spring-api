package org.uy.sdm.notificator.controllers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;
import org.uy.sdm.notificator.modules.notification.model.Channel;
import org.uy.sdm.notificator.modules.notification.model.Priority;
import org.uy.sdm.notificator.modules.notification.service.NotificationService;

import java.util.HashMap;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationControllerTest {

	@InjectMocks
	private NotificationController notificationController;

	@Mock
	private NotificationService notificationService;

	private final NotificationDto testNotificationDto = new NotificationDto();
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@BeforeEach
	public void setup(){
		testNotificationDto.setRecipient("Test Recipient");
		testNotificationDto.setSubject("Test Subject");
		testNotificationDto.setChannel(Channel.EMAIL.name());
		testNotificationDto.setBody("Test body");
		testNotificationDto.setPriority(Priority.MEDIUM.name());
		testNotificationDto.setMetadata(new HashMap<>());
	}

	@Test
	public void createNotificationAllData() {
		when(notificationService.addNotification(any())).thenReturn(testNotificationDto);
		assertNotNull(notificationController.addNotification(testNotificationDto));
	}

	@Test
	void createNotificationMissingData() {
		testNotificationDto.setRecipient(null);
		Set<ConstraintViolation<NotificationDto>> violations = validator.validate(testNotificationDto);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream()
			.anyMatch(v -> v.getPropertyPath().toString().equals("recipient")));
	}

}
