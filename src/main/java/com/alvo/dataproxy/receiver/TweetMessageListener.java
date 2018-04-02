package com.alvo.dataproxy.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class TweetMessageListener implements MessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(TweetMessageListener.class);

  @Override
  public void onMessage(Message message) {
    LOGGER.info("Message received: [{}]", new String(message.getBody()));
  }
}
