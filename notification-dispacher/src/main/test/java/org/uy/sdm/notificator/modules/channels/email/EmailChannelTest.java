package org.uy.sdm.notificator.modules.channels.email;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;
import org.uy.sdm.notificator.exceptions.EmailValidationException;
import org.uy.sdm.notificator.modules.notification.dto.NotificationDto;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class EmailChannelTest {

	@InjectMocks
	private EmailChannelImpl emailChannel;

	@Mock
	private JavaMailSender mailSender;

	@BeforeEach
	public void setup(){
		ReflectionTestUtils.setField(emailChannel,"noReply","noreply@test.uy");
	}

	@Test
	public void sendSuccessTest() {
		when(mailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));
		final NotificationDto notificationDto = new NotificationDto();
		notificationDto.setId(1L);
		notificationDto.setRecipient("test@email.com");
		notificationDto.setSubject("Test Subject");
		notificationDto.setBody("Test Body");
		emailChannel.send(notificationDto);
		verify(mailSender,atLeastOnce()).send(any(MimeMessage.class));
	}

	@Test
	public void sendFailTest() {
		final NotificationDto notificationDto = new NotificationDto();
		notificationDto.setId(1L);
		notificationDto.setRecipient("test@email.com");
		notificationDto.setSubject("Test Subject");
		notificationDto.setBody("Test Body");
		assertThrows(
			RuntimeException.class,
			() -> emailChannel.send(notificationDto)
		);
	}

	@Test
	public void sendNoSubjectTest() {
		final NotificationDto notificationDto = new NotificationDto();
		notificationDto.setId(1L);
		notificationDto.setRecipient("test@email.com");
		notificationDto.setBody("Test Body");
		assertThrows(
			EmailValidationException.class,
			() -> emailChannel.send(notificationDto)
		);
	}

	@Test
	public void sendInvalidRecipientTest() {
		final NotificationDto notificationDto = new NotificationDto();
		notificationDto.setId(1L);
		notificationDto.setRecipient("Email invalido");
		notificationDto.setSubject("Test Subject");
		notificationDto.setBody("Test Body");
		assertThrows(
			EmailValidationException.class,
			() -> emailChannel.send(notificationDto)
		);
	}

}

