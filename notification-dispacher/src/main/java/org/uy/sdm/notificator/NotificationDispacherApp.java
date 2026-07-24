package org.uy.sdm.notificator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@Slf4j
public class NotificationDispacherApp {

	public static void main(String[] args) {
		SpringApplication.run(NotificationDispacherApp.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void logStartup() {
		log.info("\uD83D\uDE80 Application started Successfully!! ✅.");
	}
}
