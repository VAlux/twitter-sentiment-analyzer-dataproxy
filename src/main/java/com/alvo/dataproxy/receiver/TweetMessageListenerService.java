package com.alvo.dataproxy.receiver;

import com.alvo.dataproxy.model.Tweet;
import com.alvo.dataproxy.repository.TweetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class TweetMessageListenerService implements MessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(TweetMessageListenerService.class);

  private final ObjectMapper objectMapper;
  private final TweetRepository tweetRepository;

  @Autowired
  public TweetMessageListenerService(ObjectMapper objectMapper,
                                     TweetRepository tweetRepository) {
    this.objectMapper = objectMapper;
    this.tweetRepository = tweetRepository;
  }

  @Override
  public void onMessage(Message message) {
    final String body = new String(message.getBody());
    unmarshalTweet(body).ifPresent(this::persistTweet);
  }

  private void persistTweet(Tweet tweet) {
    tweetRepository
        .save(tweet)
        .subscribe(savedTweet -> LOGGER.info("Tweet saved: [{}]", savedTweet));
  }

  private Optional<Tweet> unmarshalTweet(String content) {
    try {
      return Optional.of(objectMapper.readValue(content, Tweet.class));
    } catch (IOException e) {
      LOGGER.error("Error un-marshaling tweet: {}", e.getMessage());
      return Optional.empty();
    }
  }
}
