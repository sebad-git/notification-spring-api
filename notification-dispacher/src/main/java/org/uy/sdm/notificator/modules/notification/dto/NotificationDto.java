package org.uy.sdm.notificator.modules.notification.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

@Data
public class NotificationDto implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private Long id;
	@NotBlank(message = "El campo 'recipient' es requerido")
	private String recipient;
	@NotBlank(message = "El campo 'channel' es requerido")
	private String channel;
	@NotBlank(message = "El campo 'subject' es requerido")
	private String subject;
	@NotBlank(message = "El campo 'body' es requerido")
	private String body;
	@NotBlank(message = "El campo 'priority' es requerido")
	private String priority;
	private HashMap<String,Object> metadata;
}
