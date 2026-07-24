package org.uy.sdm.notificator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@Slf4j
public class NotificationDispatcherApp {

	public static void main(String[] args) {
		SpringApplication.run(NotificationDispatcherApp.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void logStartup() {
		log.info("\uD83D\uDE80 Application [Notification Dispatcher] started Successfully!! ✅.");
	}

	@Bean
	public ApplicationRunner initializeQueues(RabbitAdmin rabbitAdmin, Queue myQueue) {
		return args -> rabbitAdmin.declareQueue(myQueue);
	}
}
