package org.uy.sdm.notificator.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
@RequiredArgsConstructor
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

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		final RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(jsonMessageConverter());
		return template;
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
		final ConnectionFactory connectionFactory,
		final MessageConverter jsonMessageConverter) {
		final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(jsonMessageConverter);
		return factory;
	}
}
