package com.alvo.dataproxy.config;

import com.alvo.dataproxy.receiver.TweetMessageListenerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataProxyConfig {

  @Value("${spring.rabbitmq.host}")
  private String rabbitmqHost;
  @Value("${spring.rabbitmq.port}")
  private int rabbitmqPort;
  @Value("${spring.rabbitmq.username}")
  private String rabbitmqUsername;
  @Value("${spring.rabbitmq.password}")
  private String rabbitmqPassword;
  @Value("${spring.rabbitmq.queue.name}")
  private String queueName;
  @Value("${spring.rabbitmq.concurrent.consumers:1}")
  private Integer concurrentConsumers;

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public ConnectionFactory connectionFactory() {
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitmqHost, rabbitmqPort);
    connectionFactory.setUsername(rabbitmqUsername);
    connectionFactory.setPassword(rabbitmqPassword);
    return connectionFactory;
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public Queue tweetQueue() {
    return new Queue(queueName);
  }

  @Bean
  public SimpleMessageListenerContainer rabbitMessageListenerContainer(TweetMessageListenerService messageListener) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
    container.setQueues(tweetQueue());
    container.setConcurrentConsumers(concurrentConsumers);
    container.setAcknowledgeMode(AcknowledgeMode.AUTO);
    container.setMessageListener(new MessageListenerAdapter(messageListener, messageConverter()));
    return container;
  }
}
