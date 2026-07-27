package org.uy.sdm.notificator.modules.notification.dispacher;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.uy.sdm.notificator.modules.dispatcher.DispacherRabbitConfig;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class DispacherRabbitConfigTest {

	@InjectMocks
	private DispacherRabbitConfig dispacherRabbitConfig;

	private ConnectionFactory connectionFactory;

	@BeforeEach
	public void setup(){
		connectionFactory = mock(ConnectionFactory.class);
	}

	@Test
	public void rabbitAdminTest() {
		final RabbitAdmin admin = dispacherRabbitConfig.rabbitAdmin(connectionFactory);
		assertNotNull(admin);
		assertTrue(admin.isAutoStartup());
	}

	@Test
	public void notificationQueueTest() {
		final Queue queue = dispacherRabbitConfig.notificationQueue();
		assertNotNull(queue);
		assertEquals(DispacherRabbitConfig.NOTIFICATION_QUEUE,queue.getName());
	}

	@Test
	public void notificationExchangeTest() {
		final TopicExchange exchange = dispacherRabbitConfig.notificationExchange();
		assertNotNull(exchange);
		assertEquals(DispacherRabbitConfig.NOTIFICATION_EXCHANGE,exchange.getName());
	}

	@Test
	public void notificationBindingTest() {
		final Queue queue = dispacherRabbitConfig.notificationQueue();
		final TopicExchange exchange = dispacherRabbitConfig.notificationExchange();
		final Binding binding = dispacherRabbitConfig.notificationBinding(queue, exchange);
		assertNotNull(binding);
		assertEquals(DispacherRabbitConfig.NOTIFICATION_ROUTING_KEY,binding.getRoutingKey());
		assertEquals(DispacherRabbitConfig.NOTIFICATION_EXCHANGE,binding.getExchange());
		assertEquals(DispacherRabbitConfig.NOTIFICATION_QUEUE,binding.getDestination());
	}

	@Test
	public void jsonMessageConverterTest(){
		final MessageConverter messageConverter = dispacherRabbitConfig.jsonMessageConverter();
		assertNotNull(messageConverter);
	}

	@Test
	public void rabbitTemplateTest() {
		final RabbitTemplate template = dispacherRabbitConfig.rabbitTemplate(connectionFactory);
		assertNotNull(template);
		assertInstanceOf(Jackson2JsonMessageConverter.class, template.getMessageConverter());
	}

	@Test
	public void rabbitListenerContainerFactoryTest() {
		final MessageConverter messageConverter = dispacherRabbitConfig.jsonMessageConverter();
		final SimpleRabbitListenerContainerFactory factory = dispacherRabbitConfig.rabbitListenerContainerFactory(
			connectionFactory, messageConverter);
		assertNotNull(factory);
	}
}
