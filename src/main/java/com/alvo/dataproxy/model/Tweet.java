package com.alvo.dataproxy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "tweets")
public class Tweet {

  @Id
  private String id;

  @JsonProperty("text")
  private String text;

  @JsonProperty("sentimentLevel")
  private SentimentLevel sentimentLevel;

  private Date savedAt;

  public Tweet() {
    this.setSentimentLevel(SentimentLevel.UNKNOWN);
    this.savedAt = new Date();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Date getSavedAt() {
    return savedAt;
  }

  public void setSavedAt(Date savedAt) {
    this.savedAt = savedAt;
  }

  public SentimentLevel getSentimentLevel() {
    return sentimentLevel;
  }

  public void setSentimentLevel(SentimentLevel sentimentLevel) {
    this.sentimentLevel = sentimentLevel;
  }

  @Override
  public String toString() {
    return "Tweet{" +
        "id='" + id + '\'' +
        ", text='" + text + '\'' +
        ", sentimentLevel=" + sentimentLevel +
        ", savedAt=" + savedAt +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Tweet tweet = (Tweet) o;
    return Objects.equals(getId(), tweet.getId()) &&
        Objects.equals(getText(), tweet.getText()) &&
        getSentimentLevel() == tweet.getSentimentLevel() &&
        Objects.equals(getSavedAt(), tweet.getSavedAt());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getId(), getText(), getSentimentLevel(), getSavedAt());
  }
}
