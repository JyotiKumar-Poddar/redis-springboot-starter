package com.app.pubsub.impl;

import com.app.pubsub.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.Topic;

public class MessageSender implements Sender {

    private static final Logger LOG = LoggerFactory.getLogger(MessageSender.class);

    private final StringRedisTemplate stringRedisTemplate;
    private final Topic topic;

    public MessageSender(StringRedisTemplate stringRedisTemplate, Topic topic) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.topic = topic;
    }

    @Override
    public void sendMessage(String message) {
        LOG.info("Sending message...");
        stringRedisTemplate.convertAndSend(topic.getTopic(), message);
    }

}
