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
	/**
	 * Controlador para recibir las notificaciones desde otra api.
	 * Injecta el servicio {@link NotificationService} por lombok.
	 * Recibe un {@link NotificationDto} por post.
	 */
	private final NotificationService notificationService;

	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<NotificationDto> addNotification(
		@Valid @RequestBody NotificationDto notificationDto
	) {
		final NotificationDto savedNotificationDto =
			notificationService.addNotification(notificationDto);
		return ResponseEntity.ok(savedNotificationDto);
	}
}
