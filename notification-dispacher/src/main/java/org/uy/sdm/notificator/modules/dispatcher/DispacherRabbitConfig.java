package org.uy.sdm.notificator.modules.dispatcher;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class DispacherRabbitConfig {

	public static final String NOTIFICATION_QUEUE = "notifications.queue";
	public static final String NOTIFICATION_EXCHANGE = "notifications.exchange";
	public static final String NOTIFICATION_ROUTING_KEY = "notifications.routing.key";


	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		RabbitAdmin admin = new RabbitAdmin(connectionFactory);
		admin.setAutoStartup(true);
		return admin;
	}

	@Bean
	public Queue notificationQueue() {
		System.out.println("===== CREANDO COLA notifications.queue =====");
		return QueueBuilder.durable(NOTIFICATION_QUEUE).build();
	}

	@Bean
	public TopicExchange notificationExchange() {
		return new TopicExchange(NOTIFICATION_EXCHANGE);
	}

	@Bean
	public Binding notificationBinding(Queue notificationQueue, TopicExchange notificationExchange) {
		return BindingBuilder
			.bind(notificationQueue)
			.to(notificationExchange)
			.with(NOTIFICATION_ROUTING_KEY);
	}

}
