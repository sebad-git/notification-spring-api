package org.uy.sdm.notificator.modules.notification.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.uy.sdm.notificator.modules.notification.model.Notification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class NotificationMapper {

	private static final ModelMapper NOTIFICATION_TO_DTO_MODEL_MAPPER;
	private static final ModelMapper DTO_TO_NOTIFICATION_MODEL_MAPPER;

	static {
		NOTIFICATION_TO_DTO_MODEL_MAPPER = new ModelMapper();
		NOTIFICATION_TO_DTO_MODEL_MAPPER
			.getConfiguration()
			.setMatchingStrategy(MatchingStrategies.STRICT);

		DTO_TO_NOTIFICATION_MODEL_MAPPER= new ModelMapper();
		DTO_TO_NOTIFICATION_MODEL_MAPPER
			.getConfiguration()
			.setMatchingStrategy(MatchingStrategies.LOOSE);
	}

	/**
	 * Mapea una entidad {@link Notification} a un dto {@link NotificationDto}.
	 * @param notification la entidad {@link Notification}.
	 * @return el dto {@link NotificationDto}.
	 */
	public static NotificationDto convert(Notification notification) {
		if (notification == null)
			return null;
		return DTO_TO_NOTIFICATION_MODEL_MAPPER.map(notification,NotificationDto.class);
	}

	/**
	 * Mapea un dto {@link NotificationDto} a una entidad {@link Notification}.
	 * @param notificationDto el dto {@link NotificationDto}.
	 * @return la entidad {@link Notification}.
	 */
	public static Notification convert(NotificationDto notificationDto) {
		if (notificationDto == null)
			return null;
		return NOTIFICATION_TO_DTO_MODEL_MAPPER.map(notificationDto,Notification.class);
	}
}
