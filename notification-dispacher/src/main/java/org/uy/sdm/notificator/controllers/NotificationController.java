package org.uy.sdm.notificator.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;
import org.uy.sdm.notificator.modules.notification.service.NotificationService;

@Controller
@RestController
@RequestMapping(value = Endpoints.NOTIFICATIONS, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NotificationController {
	private final NotificationService notificationService;

	/**
	 * Envia la notificacion recibida {@link NotificationDto} al servicio {@link NotificationService}.
	 */
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<NotificationDto> addNotification(
		@Valid @RequestBody NotificationDto notificationDto
	) {
		final NotificationDto savedNotificationDto =
			notificationService.addNotification(notificationDto);
		return ResponseEntity.ok(savedNotificationDto);
	}
}
